package com.example.catclinic.views;

import static android.widget.Toast.LENGTH_SHORT;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;

public class JudgementDayEditActivity extends AppCompatActivity {

    private JudgementDayController judgementDayController;
    private EditText thoughtOnTrial, evidenceFor, evidenceAgainst, finalVerdict;
    private Button submitButton;
    private ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_judgement_day);

        judgementDayController = new JudgementDayController(this);

        //declaring variables from the view

        thoughtOnTrial = findViewById(R.id.thoughtOnTrial);

        evidenceFor = findViewById(R.id.evidenceFor);

        evidenceAgainst = findViewById(R.id.evidenceAgainst);

        finalVerdict = findViewById(R.id.finalVerdict);

        submitButton = findViewById(R.id.judgementButton);

        backBtn = findViewById(R.id.backBtn_JudgementDay);


        //code to make sure if an edit is sent the
        Intent intent = getIntent();

        // pull out the extras and set them
        String thoughtIn    = intent.getStringExtra("EXTRA_THOUGHT");
        String evidenceForIn   = intent.getStringExtra("EXTRA_EVIDENCE_FOR");
        String evidenceAgainstIn = intent.getStringExtra("EXTRA_EVIDENCE_AGAINST");
        String verdictIn    = intent.getStringExtra("EXTRA_FINAL_VERDICT");
        String sessionKey = intent.getStringExtra("SESSION_KEY");
        String documentId = intent.getStringExtra("DOCUMENT_ID");

        if (thoughtIn != null)         thoughtOnTrial.setText(thoughtIn);
        if (evidenceForIn != null)     evidenceFor.setText(evidenceForIn);
        if (evidenceAgainstIn != null) evidenceAgainst.setText(evidenceAgainstIn);
        if (verdictIn != null)         finalVerdict.setText(verdictIn);


        submitButton.setText("Update");


        //submit button to submit the users input
        submitButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String thought = thoughtOnTrial.getText().toString().trim();
                String evidence = evidenceFor.getText().toString().trim();
                String denial = evidenceAgainst.getText().toString().trim();
                String verdict = finalVerdict.getText().toString().trim();


                try {
                        judgementDayController.update(sessionKey, documentId, thought, evidence, denial, verdict, new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(
                                                JudgementDayEditActivity.this,
                                                "Entry updated!",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        startActivity(new Intent(JudgementDayEditActivity.this, JudgementDayHistoryActivity.class));
                                        finish();
                                    }
                                },

                                // onFailure
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(
                                                JudgementDayEditActivity.this,
                                                "Update failed: " + e.getMessage(),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }
                        );
                    } catch (Exception e) {
                        Toast.makeText(
                                JudgementDayEditActivity.this,
                                "Encryption error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }

            }
        }));


        //clicks back button to return to homepage
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}