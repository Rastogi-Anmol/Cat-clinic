package com.example.catclinic.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.SessionManager;
import com.example.catclinic.utils.JudgementDayViewAdapter;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class JudgementDayHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JudgementDayViewAdapter adapter;
    private List<JudgementDayEntry> entryList = new ArrayList<>();
    private ImageView backBtn;
    private JudgementDayController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement_day_history);


        recyclerView = findViewById(R.id.history_list_view);
        backBtn = findViewById(R.id.backBtn_JudgementDayHistory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new JudgementDayViewAdapter(
                this,
                entryList
        );
        recyclerView.setAdapter(adapter);


        controller = new JudgementDayController(this);


        loadAllEntries();

        //backButton returns to the home page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(JudgementDayHistoryActivity.this, HomepageActivity.class));
                finish();
            }
        });
    }

    private void loadAllEntries() {
        SessionManager sessionManager = new SessionManager(this);
        String userId = sessionManager.getUserID();
        JudgementDayRepository.getInstance()
                .findAllEntriesByUser(
                        userId,
                        querySnapshot -> {

                            entryList.clear();

                            for (DocumentSnapshot docSnap : querySnapshot.getDocuments()) {
                                JudgementDayEntry entry = docSnap.toObject(JudgementDayEntry.class);
                                if (entry != null) {
                                    entry.setDocumentID(docSnap.getId());

                                    try {
                                        controller.decrypt(entry);
                                    } catch (Exception e) {
                                        Toast.makeText(JudgementDayHistoryActivity.this, "decryption failed", Toast.LENGTH_SHORT).show();
                                    }

                                    entryList.add(entry);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        },
                        e -> {
                            Toast.makeText(this, "Failed to load history: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                );
    }
}
