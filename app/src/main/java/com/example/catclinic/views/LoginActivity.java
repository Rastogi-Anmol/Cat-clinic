package com.example.catclinic.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catclinic.R;
import com.example.catclinic.controllers.LogInController;


public class LoginActivity extends AppCompatActivity {

    private LogInController logInController;
    private EditText loginUser, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginUser = findViewById(R.id.login_id);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        //create an instance of the login controller
        logInController = new LogInController(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = loginUser.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                logInController.LogIn(user, password, retrievedUser -> {
                    Toast.makeText(LoginActivity.this, "Log-in successful!", LENGTH_SHORT).show();
                    startActivity( new Intent(LoginActivity.this, JudgementDayActivity.class));
                    finish();

                }, e -> Toast.makeText(LoginActivity.this, e.getMessage(), LENGTH_SHORT).show());
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}