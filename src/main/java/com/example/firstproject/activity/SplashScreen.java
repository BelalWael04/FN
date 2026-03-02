package com.example.firstproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.R;
import com.example.firstproject.activity.utils.SessionManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SessionManager session = new SessionManager(this);

        new Handler().postDelayed(() -> {

            if(session.isLoggedIn()){
                startActivity(new Intent(this, MainActivity.class));
            }else{
                startActivity(new Intent(this, LoginScreen.class));
            }

            finish();

        },2000);
    }
}
