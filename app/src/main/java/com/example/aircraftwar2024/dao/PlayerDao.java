package com.example.aircraftwar2024.dao;

import android.content.Context;

import java.util.List;

public interface PlayerDao {
    void findById(int PlayerId);
    List<Player> getAllPlayer();
    void doAdd(Player player);
    int getMaxId();
    void sortPlayers();
    void store(Context context);
    void load(Context context);
//    void printRankList(String playerName, int score);
    void addList(String playerName, int score,Context context);

}
