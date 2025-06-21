package com.example.catclinic.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.ComfortZoneController;
import com.example.catclinic.controllers.JudgementDayController;
import com.example.catclinic.models.HistoryWrapperModel;
import com.example.catclinic.views.ComfortZoneEditActivity;
import com.example.catclinic.views.JudgementDayEditActivity;

import java.util.Objects;

public class CombinedHistoryExpandedViewHolder extends RecyclerView.ViewHolder{

        TextView activity, date, time;
        Button editBtn, deleteBtn;

        public CombinedHistoryExpandedViewHolder(@NonNull View itemView) {
            super(itemView);
            activity  = itemView.findViewById(R.id.activityNameExpanded);
            date      = itemView.findViewById(R.id.activityDateExpanded);
            time      = itemView.findViewById(R.id.activityTimeExpanded);
            editBtn   = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }

        public void bind(HistoryWrapperModel entry, Context context) {



            activity.setText(String.format("%s : %s", entry.getCollectionName(), entry.getTopic()));

            String Date = (entry.getDate() != null)
                    ? entry.getDate()
                    : "Unknown Date";


            String Time = (entry.getTime() != null)
                    ? entry.getTime()
                    : "Unknown tIME";

            date.setText(Date);
            time.setText(Time);

            if("Thought on Trial".compareTo(entry.getCollectionName()) == 0){
                editBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(context, JudgementDayEditActivity.class);
                    intent.putExtra("DOCUMENT_ID", entry.getDocumentID());
                    context.startActivity(intent);
                });

                deleteBtn.setOnClickListener(v -> {
                    JudgementDayController controller = new JudgementDayController(context);
                    controller.delete(entry.getDocumentID(),
                            unused -> Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show(),
                            e -> Toast.makeText(context, "Deletion failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                });
            }

            if("Goal".compareTo(entry.getCollectionName()) == 0){
                editBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ComfortZoneEditActivity.class);
                    intent.putExtra("DOCUMENT_ID", entry.getDocumentID());
                    context.startActivity(intent);
                });

                deleteBtn.setOnClickListener(v -> {
                    ComfortZoneController controller = new ComfortZoneController(context);
                    controller.delete(entry.getDocumentID(),
                            unused -> Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show(),
                            e -> Toast.makeText(context, "Deletion failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                });
            }




        }
    }
