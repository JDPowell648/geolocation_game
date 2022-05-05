package com.example.geogame.ui;

import static com.example.geogame.ui.SIDSingleton.getSID;
import static com.example.geogame.ui.SIDSingleton.getUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geogame.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leaderboards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        Button button1 = (Button) findViewById(R.id.BackButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        Button button2 = (Button) findViewById(R.id.refreshButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        reload();

    }

    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void reload(){
        LeaderboardData.reset();
        loadLeaderboard();
        GridView leaderBoard = findViewById(R.id.leaderboard);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, LeaderboardData.getDataArray());
        leaderBoard.setAdapter(arrayAdapter);
    }

    public void loadLeaderboard() {

        LeaderboardData.addItem("NAME");
        LeaderboardData.addItem("RANK");
        LeaderboardData.addItem("NUM");
        LeaderboardData.addItem("AVG");
        LeaderboardData.addItem("ACC");
        GenericResponse result = new GenericResponse("");
        Call<GenericResponse> call = RetrofitClient.getInstance().getMyApi().num_users(result);
        int leaderboardSize = 100;

        try
        {
            Response<GenericResponse> response = call.execute();
            GenericResponse responseInfo = response.body();
            assert responseInfo != null;
            if(Integer.parseInt(responseInfo.result()) < 100){
                leaderboardSize = Integer.parseInt(responseInfo.result());
            };
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        for (int i = 1; i <= leaderboardSize; i++){
            UserData userData = new UserData("", "", String.valueOf(i),"","","");
            Call<UserData> call2 = RetrofitClient.getInstance().getMyApi().leaderboardRequest(userData);
            try
            {
                Response<UserData> response = call2.execute();
                UserData responseInfo = response.body();
                assert responseInfo != null;

                System.out.println(responseInfo.name());

                LeaderboardData.addItem(responseInfo.name());
                LeaderboardData.addItem(String.valueOf(i));
                LeaderboardData.addItem(responseInfo.gamesPlayed());
                LeaderboardData.addItem(responseInfo.averageScore());
                LeaderboardData.addItem(responseInfo.accuracy());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

}