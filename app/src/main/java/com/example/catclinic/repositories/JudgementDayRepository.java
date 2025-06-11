package com.example.catclinic.repositories;

import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.views.JudgementDayActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LongSummaryStatistics;

public class JudgementDayRepository {

    private FirebaseFirestore db;

    private CollectionReference JudgementDayRef;

    private static JudgementDayRepository instance; //singleton instance

    private String collection = "JudgementDayEntries";

    public JudgementDayRepository() {
        db = FirebaseFirestore.getInstance();
        JudgementDayRef = db.collection(collection);
    }

    public static JudgementDayRepository getInstance(){
        if(instance == null) instance = new JudgementDayRepository();
        return instance;
    }

    public void addJudgementDayEntry(JudgementDayEntry judgementDayEntry,
                                     OnSuccessListener<JudgementDayEntry> onSuccess,
                                     OnFailureListener onFailure){
        JudgementDayRef.add(judgementDayEntry)
                .addOnSuccessListener(doc -> onSuccess.onSuccess(judgementDayEntry))
                .addOnFailureListener(e -> new Exception("Judgement Day Entry could not be saved"));
    }

    public void findLatestJudgementDayEntry(String UserID,
                                      OnSuccessListener<JudgementDayEntry> onSuccess,
                                      OnFailureListener onFailureListener){
        JudgementDayRef.whereEqualTo("userID", UserID)
                .orderBy("postingTime", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    }
                });
    }

    public void findAllEntriesByUser(String userID,
                                     OnSuccessListener<QuerySnapshot> onSuccess,
                                     OnFailureListener onFailure) {
        JudgementDayRef
                .whereEqualTo("userID", userID)
                .orderBy("postingTime", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }

    public void deleteEntry(String documentId,OnSuccessListener<Void> onSuccess, OnFailureListener onFailure){
        JudgementDayRef
                .document(documentId)
                .delete()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }

    public void updateEntry(String documentID,
                            String thoughtOnTrialEdit,
                            String evidenceForEdit,
                            String evidenceAgainstEdit,
                            String finalVerdictEdit,
                            OnSuccessListener<Void> onSuccess,
                            OnFailureListener onFailure){
        JudgementDayRef
                .document(documentID)
                .update(
                        "thoughtOnTrial", thoughtOnTrialEdit,
                        "evidenceFor", evidenceForEdit,
                        "evidenceAgainst", evidenceAgainstEdit,
                        "finalVerdict", finalVerdictEdit,
                        "postingTime", FieldValue.serverTimestamp()
                )
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }
}
