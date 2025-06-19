package com.example.catclinic.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;
import com.example.catclinic.services.SessionManager;

public class HomepageActivity extends AppCompatActivity {

    private ImageButton emergency_btn, excercises_btn, history_btn, progress_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        emergency_btn = findViewById(R.id.btnEmergency);
        excercises_btn = findViewById(R.id.btnExercises);
        history_btn = findViewById(R.id.btnHistory);
        progress_btn = findViewById(R.id.btnProgress);


       history_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(HomepageActivity.this, JudgementDayHistoryActivity.class));
           }
       });


        excercises_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, JudgementDayActivity.class));
            }
        });

        emergency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager logout = new SessionManager(HomepageActivity.this);
                logout.logout();
                startActivity(new Intent(HomepageActivity.this, LoginActivity.class));
                finish();
            }
        });

        progress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, ComfortZoneActivity.class));
            }
        });

    }
}