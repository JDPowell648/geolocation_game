package com.example.geogame.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.geogame.ui.SIDSingleton.getSID;
import static com.example.geogame.ui.SIDSingleton.getUsername;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geogame.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Button button1 = (Button) findViewById(R.id.BackButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        Button button2 = (Button) findViewById(R.id.logout);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });
        getData();
    }
    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getData() {
        System.out.println("SID: " + getSID());
        UserData userData = new UserData(getUsername(), getSID(), "","","","");
        Call<UserData> call = RetrofitClient.getInstance().getMyApi().getUserData(userData);
        call.enqueue(new Callback<UserData>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData responseInfo = response.body();

                TextView usernameText = (TextView) findViewById(R.id.UsernameText);
                usernameText.setText(SIDSingleton.getUsername());

                TextView rankText = (TextView) findViewById(R.id.RankText);
                rankText.setText("Ranked #" + responseInfo.rank());

                TextView gamesPlayedText = (TextView) findViewById(R.id.GamesPlayedText);
                gamesPlayedText.setText("Games Played: " + responseInfo.gamesPlayed());

                TextView avgScoreText = (TextView) findViewById(R.id.AvgScoreText);
                avgScoreText.setText("Average Score: " + responseInfo.averageScore());

                TextView accuracyText = (TextView) findViewById(R.id.AccuracyText);
                accuracyText.setText("Guess Accuracy: " + responseInfo.accuracy());
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void logout(){
        LoginInfo loginInfo = new LoginInfo(getUsername(),"", getSID());
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().logout(loginInfo);
        call.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                openLogin();
            }

            @Override
            public void onFailure(Call<LoginInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
