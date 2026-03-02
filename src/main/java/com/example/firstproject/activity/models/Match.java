package com.example.firstproject.activity.models;

import com.google.gson.annotations.SerializedName;

public class Match {

    @SerializedName("strHomeTeam")
    private String homeTeam;

    @SerializedName("strAwayTeam")
    private String awayTeam;

    @SerializedName("dateEvent")
    private String matchDate;

    @SerializedName("strTime")
    private String matchTime;

    @SerializedName("intHomeScore")
    private String homeScore;

    @SerializedName("intAwayScore")
    private String awayScore;

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public String getScore() {
        if (homeScore != null && awayScore != null)
            return homeScore + " - " + awayScore;
        else
            return "VS";
    }
}