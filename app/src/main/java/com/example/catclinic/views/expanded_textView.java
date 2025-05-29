package com.example.catclinic.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;

public class expanded_textView extends AppCompatActivity {

    private EditText writingArea;
    private int sourceId;
    private LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expanded_text_view);

        writingArea = findViewById(R.id.thoughtOnTrial_expandedview);
        background = findViewById(R.id.expanded_text_view);

        // 1) Read incoming extras
        Intent in = getIntent();
        sourceId = in.getIntExtra("EXTRA_SOURCE_ID", -1);
        String initial = in.getStringExtra("EXTRA_TEXT");
        String hint = in.getStringExtra("EXTRA_HINT");
        if (initial != null) {
            writingArea.setText(initial);
            writingArea.setHint(hint);
        }

        // 2) Return data when “Save” is tapped
        background.setOnClickListener(v -> {
            Intent out = new Intent();
            out.putExtra("EXTRA_SOURCE_ID", sourceId);
            out.putExtra("EXTRA_TEXT", writingArea.getText().toString());
            setResult(RESULT_OK, out);
            finish();
        });
    }
}
