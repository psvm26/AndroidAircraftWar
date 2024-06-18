package com.example.aircraftwar2024.activity;

import com.example.aircraftwar2024.client.MySocket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.client.MySocket;
import com.example.aircraftwar2024.game.BaseGame;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    boolean hasMusic;
    public Handler handle;
    private boolean isShow = true;
    public static final ExecutorService threadPool = Executors.newCachedThreadPool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);
        handle = new MainHander();

        Button startButton = (Button) this.findViewById(R.id.start_button);
        Button connectButton = (Button) this.findViewById(R.id.connect_button);

        RadioGroup musicControl = (RadioGroup) this.findViewById(R.id.music_control);
        RadioButton hasMusicButton = (RadioButton) this.findViewById(R.id.music_on);
        RadioButton hasNotMusicButton = (RadioButton) this.findViewById(R.id.music_off);

        hasNotMusicButton.setChecked(true);
        hasMusicButton.setOnCheckedChangeListener((buttonView, isChecked) -> this.hasMusic = isChecked);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("hasMusic", MainActivity.this.hasMusic);
            Log.d("RadioGroupSelection", "Selected RadioButton: " + MainActivity.this.hasMusic);
            startActivity(intent);
    });

        connectButton.setOnClickListener(view -> {
            new Thread(new MySocket(handle)).start();
            AlertDialog builder = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("匹配中，请等待……")
                    .setCancelable(false)
                    .create();
            builder.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 这里模拟接收标志的操作，例如网络请求或其他异步操作
                    // 假设5秒后接收到标志
                    try {
                        Thread.sleep(5000); // 等待5秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 在主线程上取消显示 AlertDialog
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isShow) {
                                builder.dismiss();
                            }
                        }
                    });
                }
            }).start();
        });
    }

    class MainHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 1: isShow = false;
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("hasMusic", MainActivity.this.hasMusic);
                        intent.putExtra("gameType",1);
                        intent.putExtra("isOnline", true);
                        Log.d("RadioGroupSelection", "Selected RadioButton: " + MainActivity.this.hasMusic);
                        startActivity(intent);
                        break;
                case 2: BaseGame.allOverFlag = true;
                        break;
                case 3:
                    BaseGame.enemyScore = Integer.parseInt((String) msg.obj);
                    Log.d("enemy", "enemyScore:" + msg.obj);
                    break;
            }
        }
    }

}