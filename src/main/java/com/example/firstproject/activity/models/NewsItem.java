package com.example.firstproject.activity.models;


public class NewsItem {
    private String title, description, imageResId, date;

    public NewsItem() {} // Firebase requires empty constructor

    public NewsItem(String title, String description, String imageResId, String date) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageResId; }
    public String getDate() { return date; }


    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageResId) { this.imageResId = imageResId; }
    public void setDate(String date) { this.date = date; }
}
