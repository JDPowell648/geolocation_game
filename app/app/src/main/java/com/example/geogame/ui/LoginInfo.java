package com.example.geogame.ui;

public class LoginInfo {
    private String name;
    private String password;
    private String sessionId;

    public LoginInfo(String name, String password, String sessionId){
        this.name = name;
        this.password = password;
        this.sessionId = sessionId;
    }

    public String getSID(){
        return sessionId;
    }
}
