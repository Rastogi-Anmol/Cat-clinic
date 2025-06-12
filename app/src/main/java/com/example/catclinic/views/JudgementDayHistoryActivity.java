package com.example.catclinic.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayHistoryController;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.utils.JudgementDayViewAdapter;

import java.util.List;

public class JudgementDayHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JudgementDayViewAdapter adapter;
    private ImageView backBtn;
    private JudgementDayHistoryController historyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement_day_history);

        recyclerView = findViewById(R.id.history_list_view);
        backBtn      = findViewById(R.id.backBtn_JudgementDayHistory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JudgementDayViewAdapter(this, List.of());
        recyclerView.setAdapter(adapter);

        historyController = new JudgementDayHistoryController(this);

        backBtn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        historyController.startListening(
                entries -> adapter.updateList(entries),
                error   -> Toast.makeText(
                        JudgementDayHistoryActivity.this,
                        "Error loading entries: " + error.getMessage(),
                        Toast.LENGTH_LONG
                ).show()
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        historyController.stopListening();
    }
}
