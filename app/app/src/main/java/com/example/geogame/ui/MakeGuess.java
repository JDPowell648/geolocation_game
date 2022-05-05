package com.example.geogame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geogame.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MakeGuess extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    private Marker marker;
    private LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeguess);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Button makeGuessLocation = (Button) findViewById(R.id.MakeGuessLocation);
        makeGuessLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameReport();
            }
        });

        Button makeGuessBack = (Button) findViewById(R.id.MakeGuessBack);
        makeGuessBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayGame();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(MakeGuess.this);
        LatLng start = new LatLng(0, 0);
        marker = mMap.addMarker(new MarkerOptions()
                .draggable(true)
                .position(start));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        position = marker.getPosition();
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    public void openGameReport() {
        Intent intent = new Intent(this, GameReport.class);
        intent.putExtra("realLat", getIntent().getDoubleExtra("Lat", 0.0));
        intent.putExtra("realLng", getIntent().getDoubleExtra("Lng", 0.0));
        intent.putExtra("guessLat", marker.getPosition().latitude);
        intent.putExtra("guessLng", marker.getPosition().longitude);
        startActivity(intent);
        finish();
    }

    public void openPlayGame() {
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtra("Lat", getIntent().getDoubleExtra("Lat", 0.0));
        intent.putExtra("Lng", getIntent().getDoubleExtra("Lng", 0.0));
        startActivity(intent);
        finish();
    }
}
