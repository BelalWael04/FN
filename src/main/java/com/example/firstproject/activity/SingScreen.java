package com.example.firstproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingScreen extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnSignUp;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_screen);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.bt_SignUp);

        btnSignUp.setOnClickListener(v -> registerUser());
    }
    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(SingScreen.this, "الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SingScreen.this, "الرجاء إدخال بريد إلكتروني صحيح", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPass)) {
            Toast.makeText(SingScreen.this, "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
            return;
        }

        // إنشاء حساب في Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // حفظ الجلسة في SharedPreferences مباشرة
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("userEmail", user.getEmail());
                        editor.putString("userName", name);
                        editor.apply();

                        Toast.makeText(SingScreen.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();

                        // الانتقال مباشرة إلى MainActivity
                        Intent intent = new Intent(SingScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SingScreen.this, "فشل إنشاء الحساب: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}