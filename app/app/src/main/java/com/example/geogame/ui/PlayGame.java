package com.example.geogame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geogame.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewSource;

import java.util.Random;

public class PlayGame extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    StreetViewPanorama mPanorama;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        Button button1 = (Button) findViewById(R.id.BackButton);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        Button MakeGuess = (Button) findViewById(R.id.MakeGuess);
        MakeGuess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openMakeGuess();
            }
        });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        Random r = new Random();
        latitude = getIntent().getDoubleExtra("Lat", 0.0);
        longitude = getIntent().getDoubleExtra("Lng", 0.0);
        LatLng location = new LatLng(latitude, longitude);
        streetViewPanorama.setPosition(location, 1600, StreetViewSource.OUTDOOR);
        streetViewPanorama.setStreetNamesEnabled(false);

        mPanorama = streetViewPanorama;
        mPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
            @Override
            public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation) {
                if (streetViewPanoramaLocation == null) {
                    Random r = new Random();
                    latitude = -90 + (90 - -90) * r.nextDouble();
                    longitude = -180 + (90 - -180) * r.nextDouble();
                    LatLng location = new LatLng(latitude, longitude);
                    mPanorama.setPosition(location, 1600, StreetViewSource.OUTDOOR);
                }
            }
        });
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
    public void openMakeGuess() {
        Intent intent = new Intent(this, MakeGuess.class);
        intent.putExtra("Lat", latitude);
        intent.putExtra("Lng", longitude);
        startActivity(intent);
        finish();
    }
}
