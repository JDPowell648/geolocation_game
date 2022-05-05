package com.example.geogame.ui;

public class GenericResponse {
    private String result;

    public GenericResponse(String result) {
        this.result = result;
    }

    public String result() {
        return result;
    }

    public void result(String result) {
        this.result = result;
    }
}
