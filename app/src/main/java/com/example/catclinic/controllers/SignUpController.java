package com.example.catclinic.controllers;


import android.content.Context;

import com.example.catclinic.models.Users;
import com.example.catclinic.services.AuthorizationManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SignUpController {

    private AuthorizationManager auth;


    public SignUpController(Context context){
        auth = new AuthorizationManager(context);
    }


    public void signUp(String username, String userID, String password, String confirmPassword, OnSuccessListener<Users> onSuccess, OnFailureListener onFailure){

        if(username.isEmpty()){
            onFailure.onFailure(new Exception("Username cannot be empty"));
            return;
        }

        if(userID.isEmpty()){
            onFailure.onFailure(new Exception("User ID cannot be empty"));
            return;
        }

        if(password.isEmpty()){
            onFailure.onFailure(new Exception("Password cannot be empty"));
            return;
        }

        if(confirmPassword.isEmpty()){
            onFailure.onFailure(new Exception("Confirm password cannot be empty"));
            return;
        }

        if(!confirmPassword.equals(password)){
            onFailure.onFailure(new Exception("Password and Confirm password does not match"));
            return;
        }


        auth.signUpUser(username, userID, password, confirmPassword, onSuccess, onFailure);

    }
}