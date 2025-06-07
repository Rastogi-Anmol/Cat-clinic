package com.example.catclinic.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.models.JudgementDayEntry;

import java.util.List;

public class JudgementDayViewAdapter extends RecyclerView.Adapter<JudgementDayViewHolder> {

    Context context;

    public JudgementDayViewAdapter(Context context, List<JudgementDayEntry> items) {
        this.context = context;
        this.items = items;
    }

    List<JudgementDayEntry> items;

    @NonNull
    @Override
    public JudgementDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JudgementDayViewHolder(LayoutInflater.from(context).inflate(R.layout.judgement_day_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JudgementDayViewHolder holder, int position) {
        holder.activity.setText(String.format("Thought on Trial: %s \n evidence For: %s \n evidence against: %s \n final verdict: %S"
                , items.get(position).getThoughtOnTrial(), items.get(position).getEvidenceFor(), items.get(position).getEvidenceAgainst()
        , items.get(position).getFinalVerdict()));
        holder.time.setText(items.get(position).getPostingTime().toDate().toString());
        holder.date.setText(items.get(position).getPostingTime().toDate().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
