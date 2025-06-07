package com.example.catclinic.services;

import static java.util.Base64.getEncoder;

import android.content.Context;
import android.os.Build;


import org.apache.commons.codec.binary.Base64;

import com.example.catclinic.models.Users;
import com.example.catclinic.repositories.usersRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AuthorizationManager {

    private final com.example.catclinic.services.SessionManager sessionManager;


    public AuthorizationManager(Context context){
        sessionManager = new SessionManager(context);
    }


    public void signUpUser(String userID,
                           String username,
                           String password,
                           OnSuccessListener<Users> onSuccess,
                           OnFailureListener onFailure){

        usersRepository.getInstance().doesUserExsist(userID, retrievedUser ->
        {
            if(retrievedUser != null){
                onFailure.onFailure(new Exception("User already exsists"));
            }
            else{
                Users signupUser = new Users(userID, username, EncryptionManager.generateHashedPassword(password),
                        EncryptionManager.generateSalt());
                usersRepository.getInstance().addUser(signupUser, onSuccess, onFailure);
                onSuccess.onSuccess(signupUser);
            }
        }, onFailure);

    }

    public void LoginUser(String userID,
                          String password,
                          OnSuccessListener<Users> onSuccess,
                          OnFailureListener onFailure){

        usersRepository.getInstance().doesUserExsist(userID, retrievedUser ->{

            if(retrievedUser != null){
                String hashedPassword = EncryptionManager.generateHashedPassword(password);
                if(retrievedUser.getHashedPassword().equals(hashedPassword) && !retrievedUser.getSalt().isEmpty())
                {
                    try {
                        sessionManager.userLoggedIn(userID, retrievedUser.getUsername(),
                                EncryptionManager.generateMasterKey(password,retrievedUser.getSalt()));
                    } catch (Exception e) {
                        onFailure.onFailure(new Exception("Encryption key generation Failed"));
                        return;
                    }
                    onSuccess.onSuccess(retrievedUser);
                }
                else{
                onFailure.onFailure(new Exception("Password is wrong"));
                }
            } else{
                onFailure.onFailure(new Exception("User does not exsist"));

            }
        },onFailure);

    }


}
