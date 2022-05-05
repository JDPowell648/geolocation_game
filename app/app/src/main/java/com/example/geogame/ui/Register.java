package com.example.geogame.ui;

import static com.example.geogame.ui.SIDSingleton.getSID;
import static com.example.geogame.ui.SIDSingleton.getUsername;
import static com.example.geogame.ui.SIDSingleton.setSID;
import static com.example.geogame.ui.SIDSingleton.setUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geogame.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        TextView message = (TextView)findViewById(R.id.messageBox);
        Button button = (Button)findViewById(R.id.create_account);
        EditText emailText = (EditText)findViewById(R.id.inputEmailAddress);
        EditText passwordText = (EditText)findViewById(R.id.inputPassword);
        EditText usernameText = (EditText)findViewById(R.id.inputUsername);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateEmail(emailText, message) && validateUsername(usernameText, message)
                   && validatePassword(passwordText, message)) {

                    message.setText(R.string.creating_account);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            register(usernameText.getText().toString(), passwordText.getText().toString());

                        }
                    }, 500);
                }
            }
        });
    }
    public void openMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
    public boolean validateEmail(EditText emailText, TextView message) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailText.getText().toString().isEmpty()) {
            message.setText(R.string.enter_email);
            return false;
        } else {
            if (emailText.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                message.setText(R.string.invalid_email);
                return false;
            }
        }
    }
    public boolean validatePassword(EditText passwordText, TextView message) {
        String passwordPattern = "^[a-zA-Z0-9]{8,}$";

        if (passwordText.getText().toString().isEmpty()) {
            message.setText(R.string.enter_password);
            return false;
        } else {
            if (passwordText.getText().toString().trim().matches(passwordPattern)) {
                return true;
            } else {
                message.setText(R.string.invalid_password);
                return false;
            }
        }
    }
    public boolean validateUsername(EditText usernameText, TextView message) {
        String usernamePattern = "^[a-zA-Z0-9]{4,}$";

        if (usernameText.getText().toString().isEmpty()) {
            message.setText(R.string.enter_username);
            return false;
        } else {
            if (usernameText.getText().toString().trim().matches(usernamePattern)) {
                return true;
            } else {
                message.setText(R.string.invalid_username);
                return false;
            }
        }
    }

    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void register(String username, String password){
        LoginInfo loginInfo = new LoginInfo(username,password, getSID());
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().createAccount(loginInfo);
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

    public void login(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(username, password, "");
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

