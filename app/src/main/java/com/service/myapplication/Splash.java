package com.service.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    ProgressBar splashProgress;
    FirebaseUser userAuthed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userAuthed = FirebaseAuth.getInstance().getCurrentUser();

        initProgressBar();
    }

    private void initProgressBar() {
        splashProgress = findViewById(R.id.splashProgress);
        ObjectAnimator.ofInt(splashProgress, "progress", 100).setDuration(5000).start();
        new Handler().postDelayed(() -> {
            goToXActivity();
            finish();
        }, 5000);
    }

    private void goToXActivity() {
        Intent intent;
        if (userAuthed != null) {
            intent = new Intent(Splash.this, HomeActivity.class);
        } else {
            intent = new Intent(Splash.this, Login.class);
        }
        startActivity(intent);
    }
}