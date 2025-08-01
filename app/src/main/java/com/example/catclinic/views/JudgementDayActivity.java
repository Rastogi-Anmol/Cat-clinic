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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;

import java.io.IOException;

public class JudgementDayActivity extends AppCompatActivity {

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



        //submit button to submit the users input
        submitButton.setOnClickListener((new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String thought = thoughtOnTrial.getText().toString().trim();
                String evidence = evidenceFor.getText().toString().trim();
                String denial = evidenceAgainst.getText().toString().trim();
                String verdict = finalVerdict.getText().toString().trim();

                try {
                    judgementDayController.createJudgmentDayEntry(thought, evidence, denial, verdict, submittedJudgment -> {
                        Toast.makeText(JudgementDayActivity.this, "judgment saved", LENGTH_SHORT).show();
                        finish();
                    }, e -> Toast.makeText(JudgementDayActivity.this, e.getMessage(), LENGTH_SHORT).show());
                } catch (IOException e) {
                    Toast.makeText(JudgementDayActivity.this,"Encryption failed 1", LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(JudgementDayActivity.this,"Encryption failed 2", LENGTH_SHORT).show();
                }
            }
        }));


        //clicks back button to return to homepage
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JudgementDayActivity.this, HomepageActivity.class));
                finish();
            }
        });

    }
}