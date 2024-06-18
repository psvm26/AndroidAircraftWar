package com.example.aircraftwar2024.client;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
import java.net.UnknownHostException;

public class MySocket extends Thread{
    private static boolean match = true;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader in;
    private Handler handler;

    private static final String TAG = "MySocket";

    public MySocket(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run(){
        try{
            socket = new Socket();

            socket.connect(new InetSocketAddress
                    ("10.250.50.200",9999),5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            writer = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream(),"utf-8")),true);
            Log.i(TAG,"connect to server");

            //接收服务器返回的数据
            Thread receiveServerMsg =  new Thread(){
                @Override
                public void run(){
                    String fromserver = null;
                    try{
                        while((fromserver = in.readLine())!=null)
                        {
                            Message msg = new Message();
                            switch (fromserver) {
                                case "match": msg.what = 1;break;
                                case "over":msg.what = 2;break;
                                default: msg.what = 3;
                            }
                            msg.obj = fromserver;
                            handler.sendMessage(msg);
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            };
            receiveServerMsg.start();

            try {
                while (!BaseGame.gameOverFlag) {
                    Thread.sleep(50);
                    int score = BaseGame.score;
                    writer.println(score);
                }
                writer.println("over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch(UnknownHostException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
