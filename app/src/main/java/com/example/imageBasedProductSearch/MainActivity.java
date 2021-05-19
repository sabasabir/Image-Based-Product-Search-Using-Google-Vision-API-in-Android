package com.example.imageBasedProductSearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.imageBasedProductSearch.utilis.Constants;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView sign_up, tGuestUser;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        login = findViewById(R.id.login_btn);
        sign_up = (TextView) findViewById(R.id.sign_up);
        tGuestUser=(TextView) findViewById(R.id.tGuestUser);

        sharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);

        if (sharedPreferences.contains(Constants.KEY_EMAIL)){
            Intent intent= new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        //Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        tGuestUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i= new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
    }

});

    }

    public void click(View view) {
        Intent i= new Intent(MainActivity.this,SignupActivity.class);
        startActivity(i);
        finish();
    }
}