package com.example.imageBasedProductSearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imageBasedProductSearch.utilis.*;

public class WelcomePageActivity extends AppCompatActivity {
    Button login, camera;
    TextView sign_up, tGuestUser;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        login = findViewById(R.id.login_btn);
        sign_up = (TextView) findViewById(R.id.sign_up);
        tGuestUser = (TextView) findViewById(R.id.tGuestUser);


        sharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);

        if (sharedPreferences.contains(Constants.KEY_EMAIL)) {
            Intent intent = new Intent(WelcomePageActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        //Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePageActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePageActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        tGuestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomePageActivity.this, HomeActivity.class);
                startActivity(i);
            }

        });


    }

    public void click(View view) {
        Intent i = new Intent(WelcomePageActivity.this, SignupActivity.class);
        startActivity(i);
        finish();
    }
}