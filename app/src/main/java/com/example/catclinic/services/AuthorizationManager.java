package com.example.catclinic.services;

import android.content.Context;

import com.example.catclinic.models.Users;
import com.example.catclinic.repositories.usersRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                Users signupUser = new Users(userID, username, generateHashedPassword(password));
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
                String hashedPassword = generateHashedPassword(password);
                if(retrievedUser.getHashedPassword().equals(hashedPassword))
                {
                    sessionManager.userLoggedIn(userID, retrievedUser.getUsername(), generateEncryptionKey(password));
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
    //standard SHA-256 code taken from
    //https://medium.com/@AlexanderObregon/what-is-sha-256-hashing-in-java-0d46dfb83888
    //generates a SHA-256 encoded password
    public static String generateHashedPassword(String password){

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for(byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static String generateEncryptionKey(String password){

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for(byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
