package com.example.geogame.ui;

public class UserData {
    private String name;
    private String sessionId;
    private String rank;
    private String gamesPlayed;
    private String averageScore;
    private String accuracy;

    public UserData(String name, String sessionId, String rank, String gamesPlayed, String averageScore, String accuracy) {
        this.name = name;
        this.sessionId = sessionId;
        this.rank = rank;
        this.gamesPlayed = gamesPlayed;
        this.averageScore = averageScore;
        this.accuracy = accuracy;
    }

    public String name() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String rank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String gamesPlayed() {
        return gamesPlayed;
    }
    public void setGamesPlayed(int gamesPlayed ) {
        this.gamesPlayed = String.valueOf(gamesPlayed);
    }

    public String averageScore() {
        return averageScore;
    }
    public void setAverageScore(double averageScore) {
        this.averageScore = String.valueOf(averageScore);
    }

    public String accuracy() {
        return accuracy;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = String.valueOf(accuracy);
    }
}
