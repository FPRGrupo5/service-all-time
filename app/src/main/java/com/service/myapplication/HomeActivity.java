package com.service.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar1;
    private BottomNavigationView bottomnavegationvista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomnavegationvista=findViewById(R.id.bottomnavegation);

        getSupportFragmentManager().beginTransaction().add(R.id.frame1,new Inicio()).commit();

        bottomnavegationvista.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.HomeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Inicio()).commit();

                        return true;
                    case R.id.ServiceFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Servicios()).commit();

                        return true;
                    case R.id.PerfilFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Perfil()).commit();

                        return true;
                    case R.id.AddSerFragement:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new Addservice()).commit();

                        return true;

                }
                return false;
            }
        });



    }




}