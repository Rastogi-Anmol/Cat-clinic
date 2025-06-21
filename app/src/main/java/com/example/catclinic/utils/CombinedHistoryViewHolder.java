package com.example.catclinic.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.models.CombinedHistoryModel;

public class CombinedHistoryViewHolder extends RecyclerView.ViewHolder {

    TextView activity, date, time;


    public CombinedHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        activity = itemView.findViewById(R.id.activityName);
        date = itemView.findViewById(R.id.activityDate);
        time = itemView.findViewById(R.id.activityTime);
    }

    public void bind(CombinedHistoryModel entry) {

        activity.setText(entry.getCollectionName() + " : "  + entry.getTopic());

        String Date = (entry.getDate() != null)
                ? entry.getDate()
                : "Unknown Date";


        String Time = (entry.getTime() != null)
                ? entry.getTime()
                : "Unknown tIME";

        date.setText(Date);
        time.setText(Time);
    }
}
