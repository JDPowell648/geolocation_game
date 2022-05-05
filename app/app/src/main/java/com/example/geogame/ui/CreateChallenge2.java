package com.example.geogame.ui;

import static com.example.geogame.ui.SIDSingleton.getSID;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.geogame.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;

import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewSource;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateChallenge2 extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    private LatLng location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge2);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        Button button1 = (Button) findViewById(R.id.Back_Button);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openCreateChallenge();
            }
        });

        Button button2 = (Button) findViewById(R.id.Submit);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        StreetViewPanorama mPanorama;
        location = new LatLng(getIntent().getDoubleExtra("Lat", 0.0), getIntent().getDoubleExtra("Lng", 0.0));
        streetViewPanorama.setPosition(location, 150, StreetViewSource.OUTDOOR);
        streetViewPanorama.setStreetNamesEnabled(false);

        mPanorama = streetViewPanorama;
        mPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
            @Override
            public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation) {
                if (streetViewPanoramaLocation == null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Outdoor view does not exist for location", Toast.LENGTH_LONG).show();
                            openCreateChallenge();
                        }
                    }, 500);
                }
            }
        });
    }

    public void openCreateChallenge(){
        Intent intent = new Intent(this, CreateChallenge.class);
        intent.putExtra("Lat", location.latitude);
        intent.putExtra("Lng", location.longitude);
        startActivity(intent);
        finish();
    }

    public void openMainMenu() {
        //TODO location.latitude and location.longitude -- kept for playgame
        createGame(location.latitude,location.longitude);
        Toast.makeText(getApplicationContext(), "Challenge Created", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void createGame(double latitude, double longitude){
        GenericResponse result = new GenericResponse("");
        Call<GenericResponse> call = RetrofitClient.getInstance().getMyApi().num_locations(result);
        int id;
        try
        {
            Response<GenericResponse> response = call.execute();
            GenericResponse responseInfo = response.body();
            assert responseInfo != null;
            id = Integer.parseInt(responseInfo.result()) + 1;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        System.out.println(id);

        GameInfo gameInfo = new GameInfo(id,latitude,longitude);
        Call<GameInfo> call2 = RetrofitClient.getInstance().getMyApi().createGame(gameInfo);
        try {
            call2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
