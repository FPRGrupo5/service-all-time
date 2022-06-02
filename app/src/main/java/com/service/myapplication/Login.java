package com.service.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText emailETxt;
    EditText passwordETxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        initEditTexts();
        getEmailFromRegister();
        initButtons();
    }

    private void initEditTexts() {
        emailETxt = findViewById(R.id.eTxt_email_L);
        passwordETxt = findViewById(R.id.eTxt_password_L);
    }

    private void initButtons() {
        Button registerBtn = findViewById(R.id.btn_goToRegister);
        registerBtn.setOnClickListener(view -> go2Register());

        Button loginBtn = findViewById(R.id.btn_login_L);
        loginBtn.setOnClickListener(view -> onLogin());

        Button forgetPasswordBtn = findViewById(R.id.btn_forgetPassword_L);
        forgetPasswordBtn.setOnClickListener(view -> onForgetPassword());
    }

    private void getEmailFromRegister() {
        String userEmail = getIntent().getStringExtra("emailToLogin");
        if (userEmail == null) {
            emailETxt.setText("");
        } else {
            emailETxt.setText(userEmail);
        }
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
        String email = emailETxt.getText().toString();

        if (email.isEmpty()) {
            emailETxt.setError(getText(R.string.required));
            emailETxt.setFocusable(true);
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(
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
    }

    private void onLogin() {
        String password = passwordETxt.getText().toString();
        String email = emailETxt.getText().toString();

        if (password.isEmpty()) {
            passwordETxt.setError(getText(R.string.required));
            passwordETxt.setActivated(true);
        } else if (email.isEmpty()) {
            emailETxt.setError(getText(R.string.required));
            emailETxt.setActivated(true);
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            go2Home();
                        } else {
                            Snackbar.make(findViewById(R.id.scrollView_Register),
                                    R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}