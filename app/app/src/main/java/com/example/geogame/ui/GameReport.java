package com.example.geogame.ui;

import static com.example.geogame.ui.SIDSingleton.getSID;
import static com.example.geogame.ui.SIDSingleton.getUsername;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geogame.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameReport extends AppCompatActivity implements OnMapReadyCallback{

    private double realLat;
    private double realLng;
    private double guessLat;
    private double guessLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamereport);

        realLat = getIntent().getDoubleExtra("realLat", 0.0);
        realLng = getIntent().getDoubleExtra("realLng", 0.0);
        guessLat = getIntent().getDoubleExtra("guessLat", 0.0);
        guessLng = getIntent().getDoubleExtra("guessLng", 0.0);

        double distanceMiles = distance(realLat, realLng, guessLat, guessLat) / 1609;
        TextView distanceBox = (TextView) findViewById(R.id.DistanceBox);
        distanceBox.setText("Distance: " + String.valueOf(distanceMiles));

        int score = (int) getScore(distanceMiles);
        TextView scoreBox = (TextView) findViewById(R.id.ScoreBox);
        scoreBox.setText("Score: " + score);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Button mainMenu = (Button) findViewById(R.id.MainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
        UserData userdata = getData();
        userdata.setName(getUsername());
        double oldScore = Double.parseDouble(userdata.averageScore()) * Double.parseDouble(userdata.gamesPlayed());
        double newAvg = (oldScore + score) / (Integer.parseInt(userdata.gamesPlayed()) + 1);
        userdata.setAverageScore(newAvg);

        double oldDistance = Double.parseDouble(userdata.accuracy()) * Double.parseDouble(userdata.gamesPlayed());
        double newAcc = (oldDistance + distanceMiles) / (Integer.parseInt(userdata.gamesPlayed()) + 1);
        userdata.setAccuracy(newAcc);
        userdata.setGamesPlayed(Integer.parseInt(userdata.gamesPlayed())+1);
        sendData(userdata);
        //TODO insert/update score here
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng realPos = new LatLng(realLat, realLng);
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(realPos)
                .title("Actual Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(realPos));

        LatLng guessPos = new LatLng(guessLat, guessLng);
        Marker marker2 = googleMap.addMarker(new MarkerOptions()
                .position(guessPos)
                .title("Your Guess")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    private double getScore(double distanceMiles) {
        if (distanceMiles <= 1) {
            return 1000;
        } else if (1000 - distanceMiles / 5 >= 0) {
            return 1000 - distanceMiles / 5;
        } else {
            return 0;
        }
    }

    private double distance(double lat1 , double lon1, double lat2, double lon2) {
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(realLat);
        startPoint.setLongitude(realLng);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(guessLat);
        endPoint.setLongitude(guessLng);

        return startPoint.distanceTo(endPoint);
    }


    public UserData getData() {
        UserData userData = new UserData(getUsername(), getSID(), "","","","");
        Call<UserData> call = RetrofitClient.getInstance().getMyApi().getUserData(userData);
        try
        {
            Response<UserData> response = call.execute();
            return response.body();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void sendData(UserData userData) {;
        Call<UserData> call = RetrofitClient.getInstance().getMyApi().updateUserData(userData);
        try
        {
            call.execute();

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}