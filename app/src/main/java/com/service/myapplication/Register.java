package com.service.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.myapplication.models.CustomerModel;

import java.util.UUID;


public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailETxt;
    private EditText usernameETxt;
    private EditText passwordETxt;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        initEditTexts();
        initButtons();
    }

    private void initEditTexts() {
        emailETxt = findViewById(R.id.eTxt_email_R);
        usernameETxt = findViewById(R.id.eTxt_username_R);
        passwordETxt = findViewById(R.id.eTxt_password_R);
        syncUserTextAndEmail();
    }

    private void initButtons() {
        Button registerBtn = findViewById(R.id.btn_register_R);
        registerBtn.setOnClickListener(view -> onRegister());

        Button loginBtn = findViewById(R.id.btn_backToLogin);
        loginBtn.setOnClickListener(view -> go2Login(""));
    }

    private void go2Login(String email) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("emailToLogin", email);
        startActivity(intent);
    }

    private void onRegister() {
        String email = emailETxt.getText().toString();
        String password = passwordETxt.getText().toString();
        String username = usernameETxt.getText().toString();

        if (email.isEmpty()) {
            emailETxt.setError(getText(R.string.required));
            emailETxt.setActivated(true);
        } else if (password.isEmpty()) {
            passwordETxt.setError(getText(R.string.required));
            passwordETxt.setActivated(true);
        } else if (username.isEmpty()) {
            usernameETxt.setError(getText(R.string.required));
            usernameETxt.setActivated(true);
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            sendCheckVerification(user);
                            String uid = user != null ? user.getUid() : UUID.randomUUID().toString();
                            String mail = user != null ? user.getEmail() : email;
                            CustomerModel customer = new CustomerModel(uid, username, mail);
                            go2Login(user != null ? user.getEmail() : email);
                        } else {
                            Snackbar.make(findViewById(R.id.scrollView_Register),
                                    R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
                        }
                    }
            );
        }
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

    private void syncUserTextAndEmail() {
        emailETxt.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                String email = emailETxt.getText().toString();
                usernameETxt.setText(email.substring(0, email.indexOf("@")));
            }
            return true;
        });
    }

    private void onCreateUserIfNotExistsInFirebase() {

    }
}