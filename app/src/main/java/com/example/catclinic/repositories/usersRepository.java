package com.example.catclinic.repositories;

import com.example.catclinic.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;



public class usersRepository {

    private FirebaseFirestore db;

    private CollectionReference userRef;

    private static usersRepository instance;  // Singleton instance

    private String collection = "Users";


    private usersRepository(){
        db = FirebaseFirestore.getInstance();
        userRef = db.collection(collection);
    }

    public static usersRepository getInstance() {
        if (instance == null) instance = new usersRepository();
        return instance;
    }


    public void addUser(Users user, OnSuccessListener<Users> onSuccess, OnFailureListener onFailure){

        userRef.document(user.getUserId())
                .set(user)
                .addOnSuccessListener(doc -> onSuccess.onSuccess(user))
                .addOnFailureListener(e-> onFailure.onFailure(new Exception("User cannot be added")));
    }



    public void doesUserExsist(String userID, OnSuccessListener<Users> onSuccess, OnFailureListener onFailure){
        userRef.document(userID)
                .get()
                .addOnSuccessListener( doc -> onSuccess.onSuccess(doc.toObject(Users.class)))
                .addOnFailureListener(e -> new Exception("Failed to check if user exists"));
    }

}
