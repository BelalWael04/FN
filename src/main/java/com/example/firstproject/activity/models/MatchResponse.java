package com.example.firstproject.activity.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchResponse {

    @SerializedName("events")
    private List<Match> events;

    public List<Match> getEvents() {
        return events;
    }
}