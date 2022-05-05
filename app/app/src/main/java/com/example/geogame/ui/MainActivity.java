package com.example.geogame.ui;

import static com.example.geogame.ui.SIDSingleton.getSID;
import static com.example.geogame.ui.SIDSingleton.getUsername;
import static com.example.geogame.ui.SIDSingleton.setSID;
import static com.example.geogame.ui.SIDSingleton.setUsername;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geogame.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        Button button2 = (Button) findViewById(R.id.login);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        //finish();

}
    public void openRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void login() {

        EditText usernameEntry = (EditText) findViewById(R.id.username);
        setUsername(usernameEntry.getText().toString());
        EditText passwordEntry = (EditText) findViewById(R.id.password);
        String password = passwordEntry.getText().toString();

        LoginInfo loginInfo = new LoginInfo(usernameEntry.getText().toString(), password, "");
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().login(loginInfo);
        call.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                LoginInfo responseInfo = response.body();
                setSID(responseInfo.getSID());
                System.out.println(getSID());
                System.out.println(getUsername());
                openMainMenu();
            }

            @Override
            public void onFailure(Call<LoginInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });

    }
}
