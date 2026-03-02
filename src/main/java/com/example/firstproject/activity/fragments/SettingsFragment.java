package com.example.firstproject.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject.R;
import com.example.firstproject.activity.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private Button btnChangePassword, btnArabic, btnEnglish, btnFrench, btnAboutApp, btnLogout;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Firebase & SharedPreferences
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);

        // زرار التحكم
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnArabic = view.findViewById(R.id.btnArabic);
        btnEnglish = view.findViewById(R.id.btnEnglish);
        btnFrench = view.findViewById(R.id.btnFrench);
        btnAboutApp = view.findViewById(R.id.btnAboutApp);
        btnLogout = view.findViewById(R.id.btnLogout);

        // تغيير كلمة المرور
        btnChangePassword.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ChangePasswordFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // تغيير اللغة (مؤقت – يظهر رسالة)
        btnArabic.setOnClickListener(v ->
                Toast.makeText(getContext(), "تم اختيار اللغة العربية", Toast.LENGTH_SHORT).show()
        );
        btnEnglish.setOnClickListener(v ->
                Toast.makeText(getContext(), "English language selected", Toast.LENGTH_SHORT).show()
        );
        btnFrench.setOnClickListener(v ->
                Toast.makeText(getContext(), "Langue française sélectionnée", Toast.LENGTH_SHORT).show()
        );

        // عن التطبيق
        btnAboutApp.setOnClickListener(v ->
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AboutFragment())
                        .addToBackStack(null)
                        .commit()
        );

        // تسجيل الخروج
        btnLogout.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void logoutUser() {
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