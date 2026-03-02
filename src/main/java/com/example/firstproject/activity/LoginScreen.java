package com.example.firstproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignUp;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            finish();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.bt_Login);
        tvSignUp = findViewById(R.id.Create_Account);

        // زر تسجيل الدخول
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginScreen.this, "الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginScreen.this, "الرجاء إدخال بريد إلكتروني صحيح", Toast.LENGTH_SHORT).show();
                return;
            }

            // تسجيل الدخول مع Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userEmail", email);
                            editor.apply();
                            Toast.makeText(LoginScreen.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                            // الانتقال للصفحة الرئيسية أو Home
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginScreen.this, "فشل تسجيل الدخول: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // رابط الانتقال لإنشاء حساب
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, SingScreen.class);
            startActivity(intent);
        });
    }
}