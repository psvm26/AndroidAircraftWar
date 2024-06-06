package com.example.aircraftwar2024.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType = 0;
    private boolean hasMusic = false;
    public static int screenWidth,screenHeight;
    public Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);

        getScreenHW();
        handle = new MyHandle();

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            hasMusic = getIntent().getBooleanExtra("hasMusic",false);
        }
        Log.d("gameTypeGet", "Geted GameType: " + gameType);
        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        BaseGame baseGameView = null;
        if (gameType == 1) {
            baseGameView = new EasyGame(this,handle,hasMusic);
        } else if (gameType == 2) {
            baseGameView = new MediumGame(this,handle,hasMusic);
        }else {
            baseGameView = new HardGame(this,handle,hasMusic);
        }
        setContentView(baseGameView);
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    private void toRecordActivity(int score,String playerName){
        Intent intent = new Intent(GameActivity.this, RecordActivity.class);
        intent.putExtra("gameType", gameType);
        intent.putExtra("score", score);
        intent.putExtra("playerName", playerName);
        startActivity(intent);
        ActivityManager.finishActivity();
    }
    private void getname(int score){
        Log.d("scoreGet", "Geted score: " + score);
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        Log.d("scoreGet", "bulid");
        builder.setTitle("最终得分："+score+"\n请输入用户名：");
        EditText inputText = new EditText(GameActivity.this);
        builder.setView(inputText);
        builder.setPositiveButton("确认", (dialogInterface, i) -> {
            String playerName = inputText.getText().toString();
            toRecordActivity(score, playerName);
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 1:
                    Toast.makeText(GameActivity.this,"GameOver",Toast.LENGTH_SHORT).show();
                    getname((int)msg.obj);
                    break;
            }
        }
    }
}
