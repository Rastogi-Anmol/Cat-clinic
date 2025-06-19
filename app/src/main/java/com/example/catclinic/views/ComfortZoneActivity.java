package com.example.catclinic.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;
import com.example.catclinic.controllers.ComfortZoneController;

public class ComfortZoneActivity extends AppCompatActivity {


    private TextView tasksComfortable, tasksDoable, tasksGoal;
    private Button submitBtn;
    private ImageView backBtn;

    private ComfortZoneController comfortZoneController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comfort_zone);

        comfortZoneController = new ComfortZoneController(this);

        tasksComfortable = findViewById(R.id.tasksComfortable);
        tasksDoable = findViewById(R.id.tasksDoable);
        tasksGoal = findViewById(R.id.tasksGoal);
        backBtn = findViewById(R.id.backBtn_ComfortZone);
        submitBtn = findViewById(R.id.ComfortZoneButton);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comfortable = tasksComfortable.getText().toString().trim();
                String doable = tasksDoable.getText().toString().trim();
                String goal = tasksGoal.getText().toString().trim();

                try {
                    comfortZoneController.createComfortZoneEntry(comfortable, doable, goal, submittedComfortZone ->
                            Toast.makeText(ComfortZoneActivity.this, "Entry saved successfully!", Toast.LENGTH_SHORT).show(),
                            e -> Toast.makeText(ComfortZoneActivity.this, "Save failed : " + e.getMessage(), Toast.LENGTH_SHORT).show() );
                } catch (Exception e) {
                    Toast.makeText(ComfortZoneActivity.this, "Save Failed : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

        //ends activity and returns to the page that called it
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}