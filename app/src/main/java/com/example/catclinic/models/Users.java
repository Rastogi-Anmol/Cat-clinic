package com.example.catclinic.models;


public class Users {

    private String username;
    private String UserId;
    private String hashedPassword;

    //constructors

    public Users() { }

    public Users(String userId, String password){
        this.UserId = userId;
        this.hashedPassword = password;
        //if no username is provided we set the username to userID
        this.username = userId;
    }

    public Users(String userId, String username, String password){
        this.UserId = userId;
        this.hashedPassword = password;
        this.username = username;
    }

    //setters
    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    //getters
    public String getUsername(){
        return this.username;
    };

    public String getUserId(){
        return this.UserId;
    }

    public String getHashedPassword(){
        return this.hashedPassword;
    }

}
