package com.example.catclinic.utils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;

public class JudgementDayViewHolder extends RecyclerView.ViewHolder {

    TextView activity, date, time;


    public JudgementDayViewHolder(@NonNull View itemView) {
        super(itemView);
        activity = itemView.findViewById(R.id.activityName);
        date = itemView.findViewById(R.id.activityDate);
        time = itemView.findViewById(R.id.activityTime);
    }
}

