package com.example.firstproject.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firstproject.R;
import com.example.firstproject.activity.models.Match;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {

    private Context context;
    private List<Match> matchList;

    public MatchesAdapter(Context context, List<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_item_layout, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.tvHomeTeam.setText(match.getHomeTeam());
        holder.tvAwayTeam.setText(match.getAwayTeam());
        holder.tvDate.setText(match.getMatchDate());
        holder.tvTime.setText(match.getMatchTime());
        holder.tvScore.setText(match.getScore());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

        TextView tvHomeTeam, tvAwayTeam, tvDate, tvTime, tvScore;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHomeTeam = itemView.findViewById(R.id.home_team);
            tvAwayTeam = itemView.findViewById(R.id.away_team);
            tvDate = itemView.findViewById(R.id.match_date);
            tvTime = itemView.findViewById(R.id.match_time);
            tvScore = itemView.findViewById(R.id.match_score);
        }
    }
}