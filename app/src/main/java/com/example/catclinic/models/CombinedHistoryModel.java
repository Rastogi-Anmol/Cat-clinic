package com.example.catclinic.models;

public class CombinedHistoryModel {

    String collectionName;

    String documentID;

    String topic;

    String date;

    String time;

    public CombinedHistoryModel(String collectionName, String documentID, String topic, String date, String time) {
        this.collectionName = collectionName;
        this.documentID = documentID;
        this.topic = topic;
        this.date = date;
        this.time = time;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
