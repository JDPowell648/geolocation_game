package com.example.geogame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geogame.R;

import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Response;

public class ChooseGameType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosegametype);

        Button random = (Button) findViewById(R.id.RandomLocation);
        random.setOnClickListener(v -> openPlayGameRandom());

        Button playerCreated = (Button) findViewById(R.id.PlayerSubmittedLocation);
        playerCreated.setOnClickListener(v -> openPlayGameCreated());
    }

    public void openPlayGameRandom(){
        Intent intent = new Intent(this, PlayGame.class);
        startActivity(intent);
        finish();
    }

    public void openPlayGameCreated(){
        //TODO Load a random kept lat and lang
        Intent intent = new Intent(this, PlayGame.class);
        GenericResponse result = new GenericResponse("");
        Call<GenericResponse> call = RetrofitClient.getInstance().getMyApi().num_locations(result);
        int id;
        try
        {
            Response<GenericResponse> response = call.execute();
            GenericResponse responseInfo = response.body();
            assert responseInfo != null;
            id = ThreadLocalRandom.current().nextInt(1,Integer.parseInt(responseInfo.result()) + 1);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        GameInfo gameInfo = new GameInfo(id,0,0);
        Call<GameInfo> call2 = RetrofitClient.getInstance().getMyApi().getRandomGame(gameInfo);
        try
        {
            Response<GameInfo> response = call2.execute();
            GameInfo responseInfo = response.body();
            assert responseInfo != null;
            intent.putExtra("Lat",responseInfo.getLatitude());
            intent.putExtra("Lng",responseInfo.getLongitude());
            startActivity(intent);
            finish();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
