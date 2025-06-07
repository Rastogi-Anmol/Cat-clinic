package com.example.catclinic.models;


public class Users {

    private String UserID;
    private String username;
    private String hashedPassword;
    private String salt;

    //constructors

    public Users() { }

    public Users(String userId, String password){
        this.UserID = userId;
        this.hashedPassword = password;
        //if no username is provided we set the username to userID
        this.username = userId;
    }

    public Users(String userId, String username, String password){
        this.UserID = userId;
        this.hashedPassword = password;
        this.username = username;
    }

    public Users(String userID, String username, String hashedPassword, String salt) {
        UserID = userID;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    //setters
    public void setUserId(String userId) {
        this.UserID = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    //getters
    public String getUsername(){
        return this.username;
    };

    public String getUserId(){
        return this.UserID;
    }

    public String getHashedPassword(){
        return this.hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

}
