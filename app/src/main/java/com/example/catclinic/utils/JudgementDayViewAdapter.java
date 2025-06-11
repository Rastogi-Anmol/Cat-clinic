package com.example.catclinic.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.views.JudgementDayActivity;
import com.example.catclinic.views.JudgementDayEditActivity;
import com.example.catclinic.views.JudgementDayHistoryActivity;

import java.util.List;

public class JudgementDayViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL   = 0;
    private static final int TYPE_EXPANDED = 1;

    private Context context;
    private List<JudgementDayEntry> items;

    // Track the single expanded item index
    private int expandedPosition = -1;

    public JudgementDayViewAdapter(Context context, List<JudgementDayEntry> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return position == expandedPosition ? TYPE_EXPANDED : TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_EXPANDED) {
            View view = inflater.inflate(R.layout.judgement_day_view_expanded, parent, false);
            return new JudgementDayExpandedViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.judgement_day_view, parent, false);
            return new JudgementDayViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JudgementDayEntry entry = items.get(position);

        // Bind data based on current view type
        if (getItemViewType(position) == TYPE_EXPANDED) {
            JudgementDayExpandedViewHolder expandedHolder = (JudgementDayExpandedViewHolder) holder;
            expandedHolder.activity.setText(entry.getThoughtOnTrial());
            String dateTime = entry.getPostingTime().toDate().toString();
            expandedHolder.date.setText(dateTime);
            expandedHolder.time.setText(dateTime);


            expandedHolder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JudgementDayEditActivity.class);

                    // put each field you want to prefill
                    intent.putExtra("EXTRA_THOUGHT",entry.getThoughtOnTrial());
                    intent.putExtra("EXTRA_EVIDENCE_FOR",  entry.getEvidenceFor());
                    intent.putExtra("EXTRA_EVIDENCE_AGAINST", entry.getEvidenceAgainst());
                    intent.putExtra("EXTRA_FINAL_VERDICT", entry.getFinalVerdict());
                    intent.putExtra(("SESSION_KEY"), entry.getEncryptedSessionKey());
                    intent.putExtra(("DOCUMENT_ID"), entry.getDocumentID());

                    context.startActivity(intent);
                }
            });
        } else {
            JudgementDayViewHolder normalHolder = (JudgementDayViewHolder) holder;
            normalHolder.activity.setText(
                    String.format(
                            "Thought on Trial: %s\nEvidence For: %s\nEvidence Against: %s\nFinal Verdict: %s",
                            entry.getThoughtOnTrial(),
                            entry.getEvidenceFor(),
                            entry.getEvidenceAgainst(),
                            entry.getFinalVerdict()
                    )
            );
            String dateTime = entry.getPostingTime().toDate().toString();
            normalHolder.date.setText(dateTime);
            normalHolder.time.setText(dateTime);
        }

        // Use adapter position within listener, not the bind-time position
        holder.itemView.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (adapterPos == RecyclerView.NO_POSITION) return;

            int previousExpanded = expandedPosition;
            if (expandedPosition == adapterPos) {
                // collapse the currently expanded item
                expandedPosition = -1;
                notifyItemChanged(adapterPos);
            } else {
                // expand the clicked item
                expandedPosition = adapterPos;
                notifyItemChanged(previousExpanded);
                notifyItemChanged(expandedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
