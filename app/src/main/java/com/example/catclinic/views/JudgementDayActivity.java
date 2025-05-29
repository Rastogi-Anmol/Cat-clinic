package com.example.catclinic.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;
import com.example.catclinic.controllers.JudgementDayController;

public class JudgementDayActivity extends AppCompatActivity {

    private JudgementDayController judgementDayController;
    private EditText thoughtOnTrial, evidenceFor, evidenceAgainst, finalVerdict;
    private ActivityResultLauncher<Intent> expandedLauncher;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_judgement_day_activity);

        expandedLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int sourceId    = data.getIntExtra("EXTRA_SOURCE_ID", -1);
                        String newText  = data.getStringExtra("EXTRA_TEXT");
                        if (sourceId != -1 && newText != null) {
                            // Update the correct EditText
                            EditText target = findViewById(sourceId);
                            target.setText(newText);
                        }
                    }
                }
        );

        judgementDayController = new JudgementDayController(this);

        //declaring variables from the view

        thoughtOnTrial = findViewById(R.id.thoughtOnTrial);

        evidenceFor = findViewById(R.id.evidenceFor);

        evidenceAgainst = findViewById(R.id.evidenceAgainst);

        finalVerdict = findViewById(R.id.finalVerdict);

        submitButton = findViewById(R.id.judgementButton);

        submitButton.setOnClickListener((new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String thought = thoughtOnTrial.getText().toString().trim();
                String evidence = evidenceFor.getText().toString().trim();
                String denial = evidenceAgainst.getText().toString().trim();
                String verdict = finalVerdict.getText().toString().trim();

                judgementDayController.createJudgmentDayEntry(thought, evidence, denial, verdict, submittedJudgment -> {
                    Toast.makeText(JudgementDayActivity.this, "judgment saved", LENGTH_SHORT).show();
                }, e -> Toast.makeText(JudgementDayActivity.this, e.getMessage(), LENGTH_SHORT).show());
            }
        }));

        thoughtOnTrial.setOnLongClickListener(v -> {
            openEditor(thoughtOnTrial);
            return true;
        });

        evidenceFor.setOnLongClickListener(v -> {
            openEditor(evidenceFor);
            return true;
        });

        evidenceAgainst.setOnLongClickListener(v -> {
            openEditor(evidenceAgainst);
            return true;
        });

        finalVerdict.setOnLongClickListener(v -> {
            openEditor(finalVerdict);
            return true;
        });

    }

    private void openEditor(EditText which) {
        Intent i = new Intent(this, expanded_textView.class);
        i.putExtra("EXTRA_SOURCE_ID", which.getId());
        i.putExtra("EXTRA_TEXT", which.getText().toString());
        i.putExtra("EXTRA_HINT", which.getHint().toString());
        expandedLauncher.launch(i);
    }
}