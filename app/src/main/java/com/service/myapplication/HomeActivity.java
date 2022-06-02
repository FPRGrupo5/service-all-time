package com.service.myapplication;

import static com.service.myapplication.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.service.myapplication.fragments.Addservice;
import com.service.myapplication.fragments.Inicio;
import com.service.myapplication.fragments.Perfil;
import com.service.myapplication.fragments.Services;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar1;
    private BottomNavigationView bottomNavigationView;

    private int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);

        toolbar1 = findViewById(id.toolbar1);

        setSupportActionBar(toolbar1);

        bottomNavigationView = findViewById(id.bottom_navegation);

        getSupportFragmentManager().beginTransaction().add(id.frame1,new Inicio()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case id.HomeFragment:
                    getSupportFragmentManager().beginTransaction().replace(id.frame1,new Inicio()).commit();

                    return true;
                case id.ServiceFragment:
                    getSupportFragmentManager().beginTransaction().replace(id.frame1,new Services()).commit();

                    return true;
                case id.PerfilFragment:
                    getSupportFragmentManager().beginTransaction().replace(id.frame1,new Perfil()).commit();

                    return true;
                case id.AddSerFragement:
                    getSupportFragmentManager().beginTransaction().replace(id.frame1,new Addservice()).commit();

                    return true;

                default:
                    getSupportFragmentManager().beginTransaction().replace(id.frame1, new Inicio()).commit();
            }
            return false;
        });

    }
}