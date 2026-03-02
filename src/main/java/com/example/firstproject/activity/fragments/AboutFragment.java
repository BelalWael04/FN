package com.example.firstproject.activity.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.firstproject.R;

public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        TextView tvVersion = view.findViewById(R.id.tvVersion);
        TextView tvDeveloper = view.findViewById(R.id.tvDeveloper);
        TextView tvDescription = view.findViewById(R.id.tvDescription);

        tvVersion.setText("الإصدار: 1.0.0");
        tvDeveloper.setText("تطوير: بلال وائل");
        tvDescription.setText("تطبيق كرة القدم يوفر أحدث الأخبار والنتائج بشكل سريع وسهل.");

        return view;
    }
}
