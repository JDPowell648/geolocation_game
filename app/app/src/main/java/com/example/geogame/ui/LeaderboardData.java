package com.example.geogame.ui;

import java.util.ArrayList;

public class LeaderboardData {
    private static ArrayList<String> data = new ArrayList<>();

    public static ArrayList<String> getDataArray(){
        return data;
    }

    public static void addItem(String item){
        data.add(item);
    }

    public static String getItem(int index){
        return data.get(index);
    }

    public static void reset(){
        data = new ArrayList<>();
    }

    private static LeaderboardData instance;

    public static LeaderboardData getInstance() {
        if (instance == null) instance = new LeaderboardData();
        return instance;
    }

    private LeaderboardData() {
    }
}
