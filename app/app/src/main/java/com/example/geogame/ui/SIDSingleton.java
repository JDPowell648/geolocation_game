package com.example.geogame.ui;

public class SIDSingleton {
    private static String SID;
    private static String username;

    public static String getSID(){
        return SID;
    }

    public static void setSID(String sid){
        SID = sid;
    }

    public static String getUsername(){
        return username;
    }

    public static void setUsername(String userName){
        username = userName;
    }

    private static SIDSingleton instance;

    public static SIDSingleton getInstance() {
        if (instance == null)
            instance = new SIDSingleton();
        return instance;
    }

    private SIDSingleton() { }
}
