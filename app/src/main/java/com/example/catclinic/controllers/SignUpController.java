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


    public void signUp(String userID, String username, String password, String confirmPassword, OnSuccessListener<Users> onSuccess, OnFailureListener onFailure){

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

        //length of password is too small
        if(password.length() < 8)
        {
            onFailure.onFailure(new Exception("Password must be at least 8 characters long"));
            return;
        }

        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasSpecialCharacter = password.matches(".*[!@#$&].*");
        boolean hasSpace = password.matches(".*//s.*");

        if(!hasSpace)
        {
            onFailure.onFailure(new Exception("Password cannot have empty spaces in it"));
            return;
        }

        if(!hasDigit || !hasUpper || !hasLower || !hasSpecialCharacter)
        {
            onFailure.onFailure(new Exception("Password must have at least one upper case, one lower case, one digit in it and one special character"));
            return;
        }


        auth.signUpUser(userID, username, password, onSuccess, onFailure);

    }
}