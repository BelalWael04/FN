package com.example.firstproject.activity.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("eventsnextleague.php")
    Call<MatchResponse> getNextMatches(
            @Query("id") String leagueId
    );

    @GET("eventsseason.php")
    Call<MatchResponse> getAllEvents(
            @Query("id") String leagueId,
            @Query("s") String season
    );
}