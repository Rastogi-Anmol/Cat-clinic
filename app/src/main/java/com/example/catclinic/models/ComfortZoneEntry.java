package com.example.catclinic.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

public class ComfortZoneEntry {

    //exclude being uploaded to server
    @Exclude
    private String DocumentID;

    private String UserID;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public ComfortZoneEntry(String userID,
                            String username,
                            String encryptedSessionKey,
                            String tasksGoal,
                            String tasksComfortable,
                            String tasksDoable) {
        this.UserID = userID;
        this.username = username;
        this.encryptedSessionKey = encryptedSessionKey;
        this.tasksGoal = tasksGoal;
        this.tasksComfortable = tasksComfortable;
        this.tasksDoable = tasksDoable;
    }

    public String getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    public void setEncryptedSessionKey(String encryptedSessionKey) {
        this.encryptedSessionKey = encryptedSessionKey;
    }

    private String encryptedSessionKey;
    private String tasksGoal;
    private String tasksComfortable;
    private String tasksDoable;

    public ComfortZoneEntry() {
    }


    @ServerTimestamp
    private Timestamp postingTime;

    @Exclude
    public String getDocumentID() {
        return DocumentID;
    }

    @Exclude
    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getTasksGoal() {
        return tasksGoal;
    }

    public void setTasksGoal(String tasksGoal) {
        this.tasksGoal = tasksGoal;
    }

    public String getTasksComfortable() {
        return tasksComfortable;
    }

    public void setTasksComfortable(String tasksComfortable) {
        this.tasksComfortable = tasksComfortable;
    }

    public String getTasksDoable() {
        return tasksDoable;
    }

    public void setTasksDoable(String tasksDoable) {
        this.tasksDoable = tasksDoable;
    }

    public Timestamp getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(Timestamp postingTime) {
        this.postingTime = postingTime;
    }





}
