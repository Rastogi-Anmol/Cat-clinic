package com.example.catclinic.models;

import androidx.core.i18n.DateTimeFormatter;

import com.google.type.DateTime;

import java.time.LocalTime;

public class JudgementDayEntry {

    private String userID;
    private String username;

    private String journalEntry;

    private String postingTime;
    

    public JudgementDayEntry() {
    }

    public JudgementDayEntry(String userID, String username, String journalEntry, String postingTime) {
        this.userID = userID;
        this.username = username;
        this.journalEntry = journalEntry;
        this.postingTime = postingTime;
    }

    public JudgementDayEntry(String userID, String username, String journalEntry) {
        this.userID = userID;
        this.username = username;
        this.journalEntry = journalEntry;

    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
