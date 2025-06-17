package com.example.catclinic.repositories;

import android.content.Context;

import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.services.SessionManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ComfortZoneRepository {

    private FirebaseFirestore db;

    //singleton class
    private CollectionReference ComfortZoneRef;

    private final String collectionName = "ComfortZone";

    private static ComfortZoneRepository instance;


    private ComfortZoneRepository(){
        this.db = FirebaseFirestore.getInstance();
        this.ComfortZoneRef = db.collection(collectionName);
    }

    public static ComfortZoneRepository getInstance(){
        if(instance == null) instance = new ComfortZoneRepository();
        return instance;
    }


    void addComfortZoneEntry(ComfortZoneEntry comfortZoneEntry,
                             OnSuccessListener<ComfortZoneEntry> onSuccess,
                             OnFailureListener onFailure){
        ComfortZoneRef.add(comfortZoneEntry).
                addOnSuccessListener(doc -> onSuccess.onSuccess(comfortZoneEntry)).
                addOnFailureListener(e -> new Exception("Adding the Comfort Zone Entry Failed"));
    }

    

}
