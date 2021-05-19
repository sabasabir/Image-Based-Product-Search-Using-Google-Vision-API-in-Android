package com.example.imageBasedProductSearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imageBasedProductSearch.utilis.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText input_name,input_email,input_password;
    Button signup;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference fStore;
    String userID;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        input_name   = findViewById(R.id.signup_name);
        input_email      = findViewById(R.id.signup_email_address);
        input_password   = findViewById(R.id.signup_password);
//
        signup= findViewById(R.id.sign_up_btn);
        mLoginBtn   = findViewById(R.id.t_login);
//
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseDatabase.getInstance().getReference("Users");
//

        sharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
//
//
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = input_email.getText().toString().trim();
                final String password = input_password.getText().toString().trim();
                final String name    = input_name.getText().toString();

                if(TextUtils.isEmpty(email)){
                    input_name.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    input_password.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    input_password.setError("Password Must be >= 6 Characters");
                    return;
                }

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            Log.d(TAG, "onComplete: ");

                            FirebaseUser fUser = fAuth.getCurrentUser();

                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignupActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            userID = fAuth.getCurrentUser().getUid();

                            Map<String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email",email);
                            user.put("password",password);

                            fStore.child(userID)
                                    .setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: User Created");
                                            Toast.makeText(SignupActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(Constants.KEY_EMAIL, email);
                                            editor.apply();
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                }
                            });

//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: " + e.toString());
//                                }
//                            });

                        }else {
                            Log.d(TAG, "onComplete: " + task.getException());
                            Toast.makeText(SignupActivity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }
}
