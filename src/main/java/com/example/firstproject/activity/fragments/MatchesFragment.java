package com.example.firstproject.activity.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.firstproject.R;
import com.example.firstproject.activity.adapter.MatchesAdapter;
import com.example.firstproject.activity.models.Match;
import com.example.firstproject.activity.models.MatchResponse;
import com.example.firstproject.activity.models.ApiService;
import java.util.ArrayList;
import java.util.List;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFragment extends Fragment {

    private RecyclerView matchesRecyclerView;
    private MatchesAdapter matchesAdapter;
    private List<Match> matchesList;

    public MatchesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        matchesRecyclerView = view.findViewById(R.id.recycler_matches_home);
        matchesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        matchesList = new ArrayList<>();
        matchesAdapter = new MatchesAdapter(getContext(), matchesList);
        matchesRecyclerView.setAdapter(matchesAdapter);

        loadMatches();

        return view;
    }

    private void loadMatches() {

        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        apiService.getAllEvents("4328", "2023-2024")
                .enqueue(new Callback<MatchResponse>() {
                    @Override
                    public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getEvents() != null) {
                            matchesList.clear();
                            matchesList.addAll(response.body().getEvents());
                            matchesAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Matches loaded: " + matchesList.size(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MatchResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}