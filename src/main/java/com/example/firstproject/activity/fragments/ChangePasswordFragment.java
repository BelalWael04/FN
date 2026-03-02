package com.example.firstproject.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject.R;
import com.example.firstproject.activity.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

public class ChangePasswordFragment extends Fragment {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);

        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnChangePassword = view.findViewById(R.id.btnSavePassword);

        btnChangePassword.setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {
        String oldPass = etOldPassword.getText().toString().trim();
        String newPass = etNewPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(getContext(), "الرجاء ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(getContext(), "كلمة المرور الجديدة غير متطابقة", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null || user.getEmail() == null) {
            Toast.makeText(getContext(), "خطأ: المستخدم غير موجود", Toast.LENGTH_SHORT).show();
            return;
        }

        // إعادة المصادقة بكلمة المرور القديمة
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // تغيير كلمة المرور الجديدة
                user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(getContext(), "تم تغيير كلمة المرور بنجاح", Toast.LENGTH_SHORT).show();
                        logoutAfterPasswordChange();
                    } else {
                        Toast.makeText(getContext(), "فشل تغيير كلمة المرور: " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "كلمة المرور القديمة غير صحيحة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutAfterPasswordChange() {
        // مسح SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // تسجيل خروج من Firebase
        mAuth.signOut();

        // الانتقال إلى LoginScreen ومنع الرجوع
        Intent intent = new Intent(requireActivity(), LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}