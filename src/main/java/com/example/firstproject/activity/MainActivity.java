package com.example.firstproject.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.firstproject.R;
import com.example.firstproject.activity.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;

                if (id == R.id.nav_home) {
                    fragment = new HomeFragment();
                } else if (id == R.id.nav_news) {
                    fragment = new NewsFragment();
                } else if (id == R.id.nav_matches) {
                    fragment = new MatchesFragment();
                } else if (id == R.id.nav_settings) {
                    fragment = new SettingsFragment();
                } else {
                    return false;
                }

                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
