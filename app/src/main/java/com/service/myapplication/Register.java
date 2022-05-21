package com.service.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends AppCompatActivity {
    private FirebaseAuth Rauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Rauth = FirebaseAuth.getInstance();

        Button registerBtn = findViewById(R.id.btn_register_R);
        registerBtn.setOnClickListener(view -> onRegister());

        Button loginBtn = findViewById(R.id.btn_backToLogin);
        loginBtn.setOnClickListener(view -> go2Login());
    }

    private void go2Login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void onRegister() {
        EditText emailETxt = findViewById(R.id.eTxt_email_R);
        EditText passwordETxt = findViewById(R.id.eTxt_password_R);
        String email = emailETxt.getText().toString();
        String password = passwordETxt.getText().toString();

        Rauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = Rauth.getCurrentUser();
                        sendCheckVerification(user);
                        go2Login();
                    } else {
                        Snackbar.make(findViewById(R.id.scrollView_Register),
                                R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
                    }
                }
                );
    }

    private void sendCheckVerification(FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(
                    this, task -> {
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(R.id.scrollView_Register),
                                    R.string.user_created, Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}