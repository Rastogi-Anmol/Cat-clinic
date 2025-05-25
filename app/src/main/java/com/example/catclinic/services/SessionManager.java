package com.example.catclinic.services;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences prefrences;

    private final SharedPreferences.Editor editor;

    private static final String isLoggedInKey = "isLoggedIn";
    private static final String encryptionKey = "encryptionKey";
    private static final String usernameKey = "username";

    public SessionManager(Context context){
        prefrences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = prefrences.edit();
    }

    public void userLoggedIn(String username, String encryptionValue)
    {
        editor.putString(usernameKey, username);
        editor.putString(encryptionKey, encryptionValue);
        editor.putBoolean(isLoggedInKey, true);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return prefrences.getBoolean(isLoggedInKey, false);
    }

    public String getEncryptionKey(){
        return prefrences.getString(encryptionKey, "");
    }

    public String username(){
        return prefrences.getString(usernameKey, "N/A");
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }


}
