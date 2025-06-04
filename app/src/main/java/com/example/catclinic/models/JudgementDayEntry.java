package com.example.catclinic.models;


public class JudgementDayEntry {

    private String userID;
    private String username;

    private String thoughtOnTrial;

    private String evidenceFor;

    private String evidenceAgainst;

    private String finalVerdict;

    private String postingTime;

    public JudgementDayEntry() {
    }


    public JudgementDayEntry(String userID,
                             String username,
                             String thoughtOnTrial,
                             String evidenceFor,
                             String evidenceAgainst,
                             String finalVerdict,
                             String postingTime) {
        this.userID = userID;
        this.username = username;
        this.thoughtOnTrial = thoughtOnTrial;
        this.evidenceFor = evidenceFor;
        this.evidenceAgainst = evidenceAgainst;
        this.finalVerdict = finalVerdict;
        this.postingTime = postingTime; //temporary
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

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getFinalVerdict() {
        return finalVerdict;
    }

    public void setFinalVerdict(String finalVerdict) {
        this.finalVerdict = finalVerdict;
    }




}
