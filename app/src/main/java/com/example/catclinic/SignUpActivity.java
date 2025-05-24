package com.example.catclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupPasswordConfirm, signupUsername;
    private Button signupButton;
    private TextView loginRedirectText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        //declaring variables from the view

        signupEmail = findViewById(R.id.signup_userid);

        signupUsername = findViewById(R.id.signup_name);

        signupPassword = findViewById(R.id.signup_password);

        signupPasswordConfirm = findViewById(R.id.signup_password_confirm);

        signupButton = findViewById(R.id.signup_button);

        loginRedirectText = findViewById(R.id.signupRedirectText);

        //when signup button is pressed and the checks are done then
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString();
                String confirm_password = signupPasswordConfirm.getText().toString();
                String username = signupUsername.getText().toString().trim();

                //set username as userid if not provided
                if(username.isEmpty()){
                    username = user;
                }

                //check if necessary fields are empty
                if(user.isEmpty()){
                    signupEmail.setError("User id cannot be empty");
                }

                if(password.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                }

                //compareto returns one if true
                if(confirm_password.compareTo(password) == 1){
                    signupPasswordConfirm.setError("Password does not match");
                }

                auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Signup Succesful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Signup Failed, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }
}