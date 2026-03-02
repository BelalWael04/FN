package com.example.firstproject.activity.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.R;
import com.example.firstproject.activity.adapter.NewsAdapter;
import com.example.firstproject.activity.models.NewsItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsList;
    private FirebaseFirestore db;
    private SQLiteDatabase localDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recycler_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        localDb = getContext().openOrCreateDatabase("footballApp", Context.MODE_PRIVATE, null);
        localDb.execSQL("CREATE TABLE IF NOT EXISTS News(title TEXT, description TEXT, imageUrl TEXT, date TEXT)");

        loadNews();

        return view;
    }

    private void loadNews() {
        new Thread(() -> db.collection("news").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                newsList.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    NewsItem item = doc.toObject(NewsItem.class);
                    newsList.add(item);

                    // حفظ الأخبار محلياً في SQLite
                    ContentValues values = new ContentValues();
                    values.put("title", item.getTitle());
                    values.put("description", item.getDescription());
                    values.put("imageUrl", item.getImageUrl());
                    values.put("date", item.getDate());
                    localDb.insert("News", null, values);
                }
                // تحديث UI على Main Thread
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                }
            }
        })).start();
    }
}