package com.example.catclinic.views;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catclinic.R;
import com.example.catclinic.controllers.ComfortZoneController;
import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.repositories.ComfortZoneRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ComfortZoneEditActivity extends AppCompatActivity {

    private EditText tasksComfortable, tasksDoable, tasksGoal;
    private Button updateBtn;
    private ImageView backBtn;

    private ComfortZoneController comfortZoneController;
    private String sessionKey;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comfort_zone);

        // Initialize controller
        comfortZoneController = new ComfortZoneController(this);

        // Bind views
        tasksComfortable = findViewById(R.id.tasksComfortable);
        tasksDoable = findViewById(R.id.tasksDoable);
        tasksGoal = findViewById(R.id.tasksGoal);
        backBtn = findViewById(R.id.backBtn_ComfortZone);
        updateBtn = findViewById(R.id.ComfortZoneButton);

        // Get document ID passed in intent
        Intent intent = getIntent();
        documentId = intent.getStringExtra("DOCUMENT_ID");

        // Retrieve the existing entry
        ComfortZoneRepository.getInstance().getComfortZoneEntry(documentId,
                new OnSuccessListener<ComfortZoneEntry>() {
                    @Override
                    public void onSuccess(ComfortZoneEntry entry) {
                        try {
                            // Decrypt stored data
                            comfortZoneController.decryptComfortZoneEntry(entry);
                        } catch (Exception e) {
                            makeText(ComfortZoneEditActivity.this,
                                    "Decryption failed: " + e.getMessage(),
                                    LENGTH_SHORT).show();
                            finish();
                            return;
                        }

                        // Populate fields with decrypted values
                        tasksComfortable.setText(entry.getTasksComfortable());
                        tasksDoable.setText(entry.getTasksDoable());
                        tasksGoal.setText(entry.getTasksGoal());
                        sessionKey = entry.getEncryptedSessionKey();

                        // Change button text to update
                        updateBtn.setText("Update");
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeText(ComfortZoneEditActivity.this,
                                "Failed to load entry: " + e.getMessage(),
                                LENGTH_SHORT).show();
                        finish();
                    }
                }
        );

        // Update on button click
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comfortable = tasksComfortable.getText().toString().trim();
                String doable = tasksDoable.getText().toString().trim();
                String goal = tasksGoal.getText().toString().trim();

                try {
                    comfortZoneController.updateComfortZoneEntry(
                            sessionKey,
                            documentId,
                            comfortable,
                            doable,
                            goal,
                            updatedId -> {
                                makeText(ComfortZoneEditActivity.this,
                                        "Entry updated successfully!",
                                        LENGTH_SHORT).show();
                                finish();
                            },
                            e -> makeText(ComfortZoneEditActivity.this,
                                    "Update failed: " + e.getMessage(),
                                    LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    makeText(ComfortZoneEditActivity.this,
                            "Encryption error: " + e.getMessage(),
                            LENGTH_SHORT).show();
                }
            }
        });

        // Back button to close activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
