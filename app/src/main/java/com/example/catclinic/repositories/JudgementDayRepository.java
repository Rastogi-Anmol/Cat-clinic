package com.example.catclinic.repositories;

import com.example.catclinic.models.JudgementDayEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LongSummaryStatistics;

public class JudgementDayRepository {

    private FirebaseFirestore db;

    private CollectionReference JudgemnentDayRef;

    private static JudgementDayRepository instance; //singleton instance

    private String collection = "JudgementDayEntries";

    public JudgementDayRepository() {
        db = FirebaseFirestore.getInstance();
        JudgemnentDayRef = db.collection(collection);
    }

    public static JudgementDayRepository getInstance(){
        if(instance == null) instance = new JudgementDayRepository();
        return instance;
    }

    public void addJudgementDayEntry(JudgementDayEntry judgementDayEntry,
                                     OnSuccessListener<JudgementDayEntry> onSuccess,
                                     OnFailureListener onFailure){
        JudgemnentDayRef.add(judgementDayEntry)
                .addOnSuccessListener(doc -> onSuccess.onSuccess(judgementDayEntry))
                .addOnFailureListener(e -> new Exception("Judgement Day Entry could not be saved"));
    }

//    public void findLatestJudgementDayEntry(String UserID,
//                                      OnSuccessListener<JudgementDayEntry> onSuccess,
//                                      OnFailureListener onFailureListener){
//        JudgementDayRef.whereEqualTo("userID", UserID)
//                .orderBy("postingTime", Query.Direction.DESCENDING)
//                .limit(1)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            JudgementDayEntry judgementDayEntry =
//                    }
//                })
//    }
}
