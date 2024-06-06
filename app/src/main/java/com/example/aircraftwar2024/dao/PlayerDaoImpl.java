package com.example.aircraftwar2024.dao;


import android.annotation.SuppressLint;
import android.content.Context;

import com.example.aircraftwar2024.dao.Player;
import com.example.aircraftwar2024.dao.PlayerDao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerDaoImpl implements PlayerDao {

    private List<Player> players;
    private int difficulty;
    private String fileName;

    public PlayerDaoImpl(int difficulty) {
        this.difficulty = difficulty;
        this.players = new ArrayList<>();
        if(this.difficulty == 1) {
            this.fileName = "easy.txt";
        } else if (this.difficulty == 2) {
            this.fileName = "medium.txt";
        } else {
            this.fileName = "hard.txt";
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public void findById(int playerId) {
        for(Player item : players) {
            if(item.getPlayId() == playerId) {
                System.out.println("Find Player: ID [" + playerId
                +"],name [" + item.getPlayerName() + "],score ["
                + item.getPlayScore() + "],time [" + item.getPlayTime() + "].");
                return;
            }
        }
        System.out.println("Can not find this player.");
    }

    @Override
    public List<Player> getAllPlayer() {
        return players;
    }

    @Override
    public void doAdd(Player player) {
        players.add(player);
    }

    @Override
    public int getMaxId() {
        int maxId = 0;
        for(Player item: players) {
            maxId = Math.max(maxId, item.getPlayId());
        }
        return maxId;
    }

    @Override
    public void sortPlayers() {
        //重写compare方法，最好加注解，不加也没事
        Collections.sort(players, (a, b) -> {
            //返回值>0交换
            return b.getPlayScore() - a.getPlayScore();
        });
    }

    public void deleteByRank(int i) {
        this.players.remove(i);
    }

    @Override
    public void store(Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            //遍历集合，得到每一个字符串数据
            for(Player  p: players) {
                //调用字符缓冲输出流对象的方法写数据
                StringBuilder sb = new StringBuilder();
                sb.append(p.getPlayId()).append(',')
                  .append(p.getPlayerName()).append(',')
                  .append(p.getPlayScore()).append(',')
                  .append(p.getPlayTime());
                fos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                fos.write('\n');
            }
            //释放资源
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String content = new String(buffer, StandardCharsets.UTF_8);
            String[] line = content.split("\n");
            for(String l : line){
                String[] parts = l.split(",");
                int playerId = Integer.parseInt(parts[0]);
                String palyerName = parts[1];
                int playerScore = Integer.parseInt(parts[2]);
                String playTime = parts[3];
                Player player = new Player(palyerName, playerId, playerScore, playTime);
                players.add(player);
            }
            fis.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void addList(String playerName, int score, Context context) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        String time = formatter.format(calendar.getTime());
        load(context);
        Player player = new Player(playerName, getMaxId() + 1, score, time);
        doAdd(player);
        sortPlayers();
        store(context);
    }

//    @Override
//    public void printRankList(String playerName, int score) {
//        addList(playerName, score);
//        store();
//        int i = 1;
//        System.out.println("---------------------------");
//        System.out.println("           排行榜           ");
//        System.out.println("---------------------------");
//        for(Player p: players) {
//            System.out.println("第" + i + "名" + "\t" + p.getPlayerName() + "\t" + p.getPlayScore() + "\t" + p.getPlayTime());
//            i++;
//        }
//    }


}
