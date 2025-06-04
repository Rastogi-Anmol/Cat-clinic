package com.example.catclinic.controllers;

import android.content.Context;

import com.example.catclinic.models.Users;
import com.example.catclinic.services.AuthorizationManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LogInController {

    private AuthorizationManager auth;

    public LogInController(Context context){
        auth = new AuthorizationManager(context);
    }


    public void LogIn(String userID,
                      String password,
                      OnSuccessListener<Users> onSuccess,
                      OnFailureListener onFailure){

        if(userID.isEmpty()){
            onFailure.onFailure(new Exception("User ID cannot be empty"));
            return;
        }

        if(password.isEmpty()){
            onFailure.onFailure(new Exception("Password cannot be empty"));
            return;
        }

        auth.LoginUser(userID, password, onSuccess, onFailure);

    }

}