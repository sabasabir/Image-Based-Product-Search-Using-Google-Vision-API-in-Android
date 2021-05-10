package com.example.imageBasedProductSearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.imageBasedProductSearch.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView sign_up, tGuestUser;

    public static String  PREFS_NAME="mypre";
    public static String PREF_EMAIL="email";
    public static String PREF_PASSWORD="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        login = findViewById(R.id.login_btn);
        sign_up = (TextView) findViewById(R.id.sign_up);
        tGuestUser=(TextView) findViewById(R.id.tGuestUser);

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
//        public void onStart(){
//            super.onStart();
//            //read username and password from SharedPreferences
//            getUser();
//        }
//        String UserEmail= Paper.book().read(Prevalent.UserEmail);
//        String UserPasswordKey= Paper.book().read(Prevalent.UserPasswordKey);
//
//        if(UserEmail!="" && UserPasswordKey!=""){
//            if(!TextUtils.isEmpty(UserEmail) && !TextUtils.isEmpty(UserPasswordKey)){
//                allowAccess(UserEmail, UserPasswordKey);
//            }
//        }

    }

    private void allowAccess(String userEmail, String userPasswordKey) {

    }


    public void click(View view) {
        Intent i= new Intent(MainActivity.this,SignupActivity.class);
        startActivity(i);
        finish();
    }
}