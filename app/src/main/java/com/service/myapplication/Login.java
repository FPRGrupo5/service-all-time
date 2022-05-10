package com.service.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private FirebaseAuth Lauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Lauth = FirebaseAuth.getInstance();

        Button registerBtn = findViewById(R.id.Register_Btn_L);
        registerBtn.setOnClickListener(view -> go2Register());
        
        Button loginBtn = findViewById(R.id.Login_Btn_L);
        loginBtn.setOnClickListener(view -> onLogin());

        Button forgetPasswordBtn = findViewById(R.id.ForgetPassword_Btn);
        forgetPasswordBtn.setOnClickListener(view -> onForgetPassword());
    }

    private void go2Register() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void go2Home() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void onForgetPassword() {
        EditText emailETxt = findViewById(R.id.Email_eTxt_L);
        String email = emailETxt.getText().toString();

        Lauth.sendPasswordResetEmail(email).addOnCompleteListener(
                this, task -> {
                    if (task.isSuccessful()) {
                        Snackbar.make(findViewById(R.id.LinearLayoutLogin),
                                        R.string.email_sent,
                                        Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(findViewById(R.id.LinearLayoutLogin),
                                        Objects.requireNonNull(task.getException()).toString(),
                                        Snackbar.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void onLogin() {
        EditText passwordETxt = findViewById(R.id.Password_eTxt_L);
        EditText emailETxt = findViewById(R.id.Email_eTxt_L);
        String password = passwordETxt.getText().toString();
        String email = emailETxt.getText().toString();

        Lauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = Lauth.getCurrentUser();
                        go2Home();
                    } else {
                        Snackbar.make(findViewById(R.id.ScrollViewRegister),
                                R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}