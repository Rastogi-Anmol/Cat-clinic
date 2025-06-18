package com.example.catclinic.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;

public class ComfortZoneActivity extends AppCompatActivity {


    private TextView tasksComfortable, tasksDoable, tasksGoal;
    private Button submitBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comfort_zone);

        tasksComfortable = findViewById(R.id.tasksComfortable);
        tasksDoable = findViewById(R.id.tasksDoable);
        tasksGoal = findViewById(R.id.tasksGoal);
        backBtn = findViewById(R.id.backBtn_ComfortZone);
        submitBtn = findViewById(R.id.ComfortZoneButton);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }
}