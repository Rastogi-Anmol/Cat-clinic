package com.example.catclinic.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.CombinedHistoryController;
import com.example.catclinic.utils.CombinedHistoryViewAdapter;

import java.util.List;

public class CombinedHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CombinedHistoryViewAdapter adapter;
    private ImageView backBtn;
    private CombinedHistoryController historyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement_day_history);

        recyclerView = findViewById(R.id.history_list_view);
        backBtn      = findViewById(R.id.backBtn_JudgementDayHistory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CombinedHistoryViewAdapter(this, List.of());
        recyclerView.setAdapter(adapter);

        historyController = new CombinedHistoryController(this);

        backBtn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        historyController.startListening(
                entries -> adapter.updateList(entries),
                error   -> Toast.makeText(
                        CombinedHistoryActivity.this,
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
