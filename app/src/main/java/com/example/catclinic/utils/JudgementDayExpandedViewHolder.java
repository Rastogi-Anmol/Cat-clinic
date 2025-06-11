package com.example.catclinic.utils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;

public class JudgementDayExpandedViewHolder extends RecyclerView.ViewHolder {
    TextView activity, date, time;
    Button editBtn, deleteBtn;

    public JudgementDayExpandedViewHolder(@NonNull View itemView) {
        super(itemView);
        activity  = itemView.findViewById(R.id.activityNameExpanded);
        date      = itemView.findViewById(R.id.activityDateExpanded);
        time      = itemView.findViewById(R.id.activityTimeExpanded);
        editBtn   = itemView.findViewById(R.id.editBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
    }
}
