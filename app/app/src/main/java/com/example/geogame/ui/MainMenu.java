package com.example.geogame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.geogame.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Button button1 = (Button) findViewById(R.id.playGameButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openChooseGameType();
            }
        });

        Button button2 = (Button) findViewById(R.id.profileButton);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openProfile();
            }
        });

        Button button3 = (Button) findViewById(R.id.createChallengeButton);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openCreateChallenge();
            }
        });

        Button button4 = (Button) findViewById(R.id.leaderboardButton);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openLeaderBoards();
            }
        });

        Button button5 = (Button) findViewById(R.id.historyButton);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This feature is not yet implemented!", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
    public void openChooseGameType(){
        Intent intent = new Intent(this, ChooseGameType.class);
        startActivity(intent);
        finish();
    }
    public void openCreateChallenge(){
        Intent intent = new Intent(this, CreateChallenge.class);
        startActivity(intent);
        finish();
    }

    public void openLeaderBoards(){
        Intent intent = new Intent(this, Leaderboards.class);
        startActivity(intent);
    }
}