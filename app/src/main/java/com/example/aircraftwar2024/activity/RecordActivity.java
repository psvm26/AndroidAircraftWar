package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.dao.Player;
import com.example.aircraftwar2024.dao.PlayerDaoImpl;
import com.example.aircraftwar2024.game.BaseGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RecordActivity extends AppCompatActivity {
private PlayerDaoImpl playerDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int gameType = 1;
        int score = 0;
        String name = "test";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActivityManager.addActivity(this);
        TextView gameTypeTextView = (TextView) findViewById(R.id.gameTypeTextView);
        Button returnButton = (Button) findViewById(R.id.returnButton);
        ListView rankList = (ListView) findViewById(R.id.rankList);
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            score= getIntent().getIntExtra("score",0);
            name = getIntent().getStringExtra("playerName");
        }
        playerDao = new PlayerDaoImpl(gameType);
        playerDao.addList(name,score,this);
        AtomicReference<List<Map<String, Object>>> rank = new AtomicReference<>(getData());
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                rank.get(),
                R.layout.list_item,
                new String[]{"排名","用户名","分数","时间"},
                new int[]{R.id.rank,R.id.playerName,R.id.score,R.id.time}
        );
        rankList.setAdapter(listItemAdapter);
        //添加单机监听
        rankList.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
            builder.setTitle("确定删除该记录吗？");
            builder.setPositiveButton("确认", (dialog, i) -> {
                playerDao.deleteByRank(position);
                playerDao.store(this);
                rank.get().clear();
                rank.get().addAll(getData());
                listItemAdapter.notifyDataSetChanged();
                Toast.makeText(RecordActivity.this,"已删除",Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("取消",(dialog, i)->{
                Toast.makeText(RecordActivity.this,"未删除",Toast.LENGTH_SHORT).show();
            });
            builder.create();
            builder.show();
        });
        List<Player> playerList = playerDao.getAllPlayer();
//        Log.d("test", "playerNumber:" + playerList.size());
//        Log.d("test", "playertime:" + playerList.get(0).getPlayTime());
        String difficulty = (gameType == 1) ? "简单" : (gameType == 2) ? "普通" : "困难";
        gameTypeTextView.setText(difficulty);
        returnButton.setOnClickListener(view -> {
            BaseGame.init();
            for (int i = 0; i < 2; i++) {
                ActivityManager.finishActivity();
            }
            });
    }
    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        List<Player> playerList = playerDao.getAllPlayer();
        Log.d("test", "getDataNumber:" + playerList.size());
        int i = 1;
        for(Player p: playerList) {
            map = new HashMap<String, Object>();
            map.put("排名",Integer.toString(i));
            map.put("用户名",p.getPlayerName());
            map.put("分数",Integer.toString(p.getPlayScore()));
            map.put("时间",p.getPlayTime());
            i = i + 1;
            listitem.add(map);
        }
        Log.d("test", "listNumber:" + listitem.size());
        return listitem;
    }
}