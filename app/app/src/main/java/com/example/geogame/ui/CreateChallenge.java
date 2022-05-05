package com.example.geogame.ui;

import android.content.Intent;
import android.os.Bundle;
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

public class CreateChallenge extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private Marker marker;
    private LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Button button1 = (Button) findViewById(R.id.BackButton);
        button1.setOnClickListener(v -> openMainMenu());

        Button button2 = (Button) findViewById(R.id.ChooseLocation);
        button2.setOnClickListener(v -> openCreateChallenge2());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setOnMarkerDragListener(CreateChallenge.this);
        LatLng start = new LatLng(getIntent().getDoubleExtra("Lat", 0.0), getIntent().getDoubleExtra("Lng", 0.0));
        marker = googleMap.addMarker(new MarkerOptions()
                .draggable(true)
                .position(start));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(start));
    }

    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void openCreateChallenge2(){
        Intent intent = new Intent(this, CreateChallenge2.class);
        position = marker.getPosition();
        intent.putExtra("Lat", position.latitude);
        intent.putExtra("Lng", position.longitude);
        startActivity(intent);
        finish();
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
}
