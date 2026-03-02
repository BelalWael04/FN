package com.example.firstproject.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firstproject.R;
import com.example.firstproject.activity.models.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private Context context;

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.date.setText(item.getDate());

        Glide.with(context)
                .load(item.getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        ImageView image;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_desc);
            date = itemView.findViewById(R.id.news_date);
            image = itemView.findViewById(R.id.news_image);
        }
    }
}