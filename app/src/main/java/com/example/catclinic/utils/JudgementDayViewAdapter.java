package com.example.catclinic.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.views.JudgementDayActivity;
import com.example.catclinic.views.JudgementDayEditActivity;
import com.example.catclinic.views.JudgementDayHistoryActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
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

        if (holder.getItemViewType() == TYPE_EXPANDED) {
            ((JudgementDayExpandedViewHolder) holder).bind(entry, context);
        } else {
            ((JudgementDayViewHolder) holder).bind(entry);
        }

        holder.itemView.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (adapterPos == RecyclerView.NO_POSITION) return;

            int previousExpanded = expandedPosition;
            if (expandedPosition == adapterPos) {
                expandedPosition = -1;
                notifyItemChanged(adapterPos);
            } else {
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

    public void updateList(List<JudgementDayEntry> newItems) {
        this.items = new ArrayList<>(newItems);
        expandedPosition = -1;
        notifyDataSetChanged();
    }


}
