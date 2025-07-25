package com.example.catclinic.repositories;

import android.content.Context;

import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.services.SessionManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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


    public void addComfortZoneEntry(ComfortZoneEntry comfortZoneEntry,
                             OnSuccessListener<ComfortZoneEntry> onSuccess,
                             OnFailureListener onFailure){
        ComfortZoneRef.add(comfortZoneEntry).
                addOnSuccessListener(doc -> onSuccess.onSuccess(comfortZoneEntry)).
                addOnFailureListener(e -> new Exception("Adding the Comfort Zone Entry Failed"));
    }

    public void deleteComfortZoneEntry(String documentID,
                                OnSuccessListener<String> onSuccess,
                                OnFailureListener onFailure){
        ComfortZoneRef.document(documentID)
                .delete()
                .addOnSuccessListener(doc -> onSuccess.onSuccess(documentID))
                .addOnFailureListener(e -> new Exception("Comfort Zone entry deletion failed"));
    }

    public void updateComfortZoneEntry(String documentID,
                                String tasksGoal,
                                String tasksComfortable,
                                String tasksDoable,
                                OnSuccessListener<String> onSuccess,
                                OnFailureListener onFailure){
        ComfortZoneRef.document(documentID)
                .update("tasksGoal", tasksGoal,
                        "tasksComfortable", tasksComfortable
                ,"tasksDoable", tasksDoable
                ,"postingTime", FieldValue.serverTimestamp())
                .addOnSuccessListener(doc -> onSuccess.onSuccess(documentID))
                .addOnFailureListener(e -> new Exception("Update for Comfort Zone Entry Failed"));
    }

    public ListenerRegistration listenToEntries(
            String userId,
            EventListener<QuerySnapshot> listener
    ) {
        return ComfortZoneRef
                .whereEqualTo("userID", userId)
                .orderBy("postingTime", Query.Direction.DESCENDING)
                .addSnapshotListener(listener);
    }

    public void getComfortZoneEntry(String documentID,
                                     OnSuccessListener<ComfortZoneEntry> onSuccess,
                                     OnFailureListener onFailure){
        ComfortZoneRef.document(documentID)
                .get().
                addOnSuccessListener(doc -> onSuccess.onSuccess(doc.toObject(ComfortZoneEntry.class)))
                .addOnFailureListener(onFailure);
    }

    /**
     * Stop a previously-registered realtime listener.
     */
    public void stopListening(ListenerRegistration registration) {
        if (registration != null) registration.remove();
    }

}
