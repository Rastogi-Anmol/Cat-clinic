package com.example.catclinic.models;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.Exclude;

public class JudgementDayEntry {


    @Exclude
    private String documentID;

    private String userID;
    private String username;

    private String thoughtOnTrial;

    private String evidenceFor;

    private String evidenceAgainst;

    private String finalVerdict;
    @ServerTimestamp
    private Timestamp postingTime;


    public JudgementDayEntry(String documentID,
                             String userID,
                             String username,
                             String thoughtOnTrial,
                             String evidenceFor,
                             String evidenceAgainst,
                             String finalVerdict,
                             Timestamp postingTime) {
        this.documentID = documentID;
        this.userID = userID;
        this.username = username;
        this.thoughtOnTrial = thoughtOnTrial;
        this.evidenceFor = evidenceFor;
        this.evidenceAgainst = evidenceAgainst;
        this.finalVerdict = finalVerdict;
        this.postingTime = postingTime;
    }

    public JudgementDayEntry() {
    }


    public JudgementDayEntry(String userID,
                             String username,
                             String thoughtOnTrial,
                             String evidenceFor,
                             String evidenceAgainst,
                             String finalVerdict) {
        this.userID = userID;
        this.username = username;
        this.thoughtOnTrial = thoughtOnTrial;
        this.evidenceFor = evidenceFor;
        this.evidenceAgainst = evidenceAgainst;
        this.finalVerdict = finalVerdict;
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

    public String getThoughtOnTrial() {
        return thoughtOnTrial;
    }

    public void setThoughtOnTrial(String thoughtOnTrial) {
        this.thoughtOnTrial = thoughtOnTrial;
    }

    public String getEvidenceFor() {
        return evidenceFor;
    }

    public void setEvidenceFor(String evidenceFor) {
        this.evidenceFor = evidenceFor;
    }

    public String getEvidenceAgainst() {
        return evidenceAgainst;
    }

    public void setEvidenceAgainst(String evidenceAgainst) {
        this.evidenceAgainst = evidenceAgainst;
    }

    public Timestamp getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(Timestamp postingTime) {
        this.postingTime = postingTime;
    }

    public String getFinalVerdict() {
        return finalVerdict;
    }

    public void setFinalVerdict(String finalVerdict) {
        this.finalVerdict = finalVerdict;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    @Exclude
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
