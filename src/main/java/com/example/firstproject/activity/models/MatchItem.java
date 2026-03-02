package com.example.firstproject.activity.models;

public class MatchItem {
    private String homeTeam, awayTeam, matchDate, matchTime, score;

    public MatchItem() {} // مطلوب للفيريبيز

    public MatchItem(String homeTeam, String awayTeam, String matchDate, String matchTime, String score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.score = score;
    }

    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public String getMatchDate() { return matchDate; }
    public String getMatchTime() { return matchTime; }
    public String getScore() { return score; }

    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }
    public void setMatchDate(String matchDate) { this.matchDate = matchDate; }
    public void setMatchTime(String matchTime) { this.matchTime = matchTime; }
    public void setScore(String score) { this.score = score; }
}