package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;

public class OnlineResultActivity extends AppCompatActivity {
    TextView enemyScore;
    TextView resultShow;
    private static final String TAG = "OnlineResult";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_result);
        ActivityManager.addActivity(this);
        handler = new MyHandler();

        Log.i(TAG, "onCreate: isCreate");
        TextView onlineScore = (TextView) findViewById(R.id.onlineScore);
        enemyScore = (TextView) findViewById(R.id.enemyScore);
        resultShow = (TextView) findViewById(R.id.resultShow);
        Button returnButton = (Button) findViewById(R.id.returnButton1);
        onlineScore.setText("你的分数：" + BaseGame.score);
        new EnemyScoreUpdateThread().start();
        returnButton.setOnClickListener(view -> {
            BaseGame.init();
            ActivityManager.finishActivity();
        });
    }

    //写一个新的线程每隔一秒发送一次消息,这样做会和系统时间相差1秒
    public class EnemyScoreUpdateThread extends Thread{
        @Override
        public void run() {
            super.run();
            Message msg1 = new Message();
            msg1.what = 2;
            handler.sendMessage(msg1);
            do{
                try {
                    Thread.sleep(50);
                    Message msg2 = new Message();
                    msg2.what = 1;
                    handler.sendMessage(msg2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (!BaseGame.allOverFlag);
            Message msg3 = new Message();
            msg3.what = 3;
            handler.sendMessage(msg3);
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    enemyScore.setText("对手的分数："+ BaseGame.enemyScore);
                    break;
                case 2:
                    resultShow.setText("对战进行中……");
                    break;
                case 3:
                    if(BaseGame.score > BaseGame.enemyScore) {
                        resultShow.setText("你赢了");
                    }else if(BaseGame.score < BaseGame.enemyScore) {
                        resultShow.setText("你输了");
                    }else {
                        resultShow.setText("平局");
                    }
                    break;
            }
        }
    }

}