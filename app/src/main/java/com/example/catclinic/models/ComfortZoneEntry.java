package com.example.catclinic.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

public class ComfortZoneEntry {

    //exclude being uploaded to server
    @Exclude
    private String DocumentID;

    private String sessionKey;
    private String tasksGoal;
    private String tasksComfortable;
    private String tasksDoable;

    @Exclude
    public String getDocumentID() {
        return DocumentID;
    }

    @Exclude
    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
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

    @ServerTimestamp
    private Timestamp postingTime;



}
