package com.example.imageBasedProductSearch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imageBasedProductSearch.utilis.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button login;
    TextView t_signup,forgotTextLink;
    FirebaseAuth fAuth;
String parentDbName= "User";
    TextView adminLink, notAdminLink;
    //private InputValidation inputValidation;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email_address);
        mPassword = findViewById(R.id.login_password);

        fAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login_btn);

        t_signup = findViewById(R.id.t_sign_up);
        forgotTextLink = findViewById(R.id.forgetPassword);

        adminLink= findViewById(R.id.admin);
        notAdminLink= findViewById(R.id.not_admin_panel_link);

        sharedPreferences = getSharedPreferences(Constants.PREF, MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

//                if(parentDbName.equals("Admins")){
//                    Toast.makeText(LoginActivity.this, "Welcome Admin, You are Logged in Successfully", Toast.LENGTH_SHORT);
//                    Intent intent= new Intent (LoginActivity.this, AdminAddNewProductsActivity.class);
//                    startActivity(intent);
//                }
//                else if(parentDbName.equals("Users")){
//                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT);
//                    Intent intent= new Intent (LoginActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                }

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    if(checkBoxRememberMe.isChecked()){
//                        Paper.book().write(Prevalent.UserEmail, email);
//                        Paper.book().write(Prevalent.UserPasswordKey, password);
//
//                    }
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.KEY_EMAIL, email);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Error ! You need to create a new account. " +
                                    Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
//adminLink.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        login.setText("Login Admin");
//        adminLink.setVisibility(View.INVISIBLE);
//        notAdminLink.setVisibility(View.VISIBLE);
//        parentDbName= "Admins";
//    }
//});
//notAdminLink.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        login.setText("Login");
//        adminLink.setVisibility(View.VISIBLE);
//        notAdminLink.setVisibility(View.INVISIBLE);
//        parentDbName= "Users";
//    }
//});

        t_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


    }

}
