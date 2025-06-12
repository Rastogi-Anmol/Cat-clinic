package com.example.catclinic.utils;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.views.JudgementDayEditActivity;

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

    public void bind(JudgementDayEntry entry, Context context) {
        activity.setText(entry.getThoughtOnTrial());

        String dateTime = (entry.getPostingTime() != null)
                ? entry.getPostingTime().toDate().toString()
                : "Unknown Time";

        date.setText(dateTime);
        time.setText(dateTime);

        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, JudgementDayEditActivity.class);
            intent.putExtra("EXTRA_THOUGHT", entry.getThoughtOnTrial());
            intent.putExtra("EXTRA_EVIDENCE_FOR", entry.getEvidenceFor());
            intent.putExtra("EXTRA_EVIDENCE_AGAINST", entry.getEvidenceAgainst());
            intent.putExtra("EXTRA_FINAL_VERDICT", entry.getFinalVerdict());
            intent.putExtra("SESSION_KEY", entry.getEncryptedSessionKey());
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
}
