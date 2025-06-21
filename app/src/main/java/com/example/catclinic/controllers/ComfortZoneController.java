package com.example.catclinic.controllers;

import android.content.Context;

import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.repositories.ComfortZoneRepository;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.EncryptionManager;
import com.example.catclinic.services.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ComfortZoneController {

    private final SessionManager sessionManager;
    private final EncryptionManager encryptionManager;

    public ComfortZoneController(Context context)
    {
        this.sessionManager = new SessionManager(context);
        this.encryptionManager = new EncryptionManager(context);
    }

    public void createComfortZoneEntry(String Comfortable,
                                       String Doable,
                                       String Goal,
                                       OnSuccessListener<ComfortZoneEntry> onSuccess,
                                       OnFailureListener onFailure) throws Exception{


        if(Comfortable.isEmpty())
        {
            onFailure.onFailure(new Exception("Things you are comfortable with cannot be empty!"));
            return;
        }

        if(Doable.isEmpty())
        {
            onFailure.onFailure(new Exception("Things yuo deem doable cannot be empty!"));
            return;
        }

        if(Goal.isEmpty())
        {
            onFailure.onFailure(new Exception("The Goal that you want to achieve cannot be empty!"));
            return;
        }


        //create the sessionKey for encryption of the entry
        String sessionKey = EncryptionManager.generateSessionKey();
        String encryptedSessionKey = encryptionManager.encryptSessionKey(sessionKey);

        String comfortableEncrypted = encryptionManager.encryptData(Comfortable, sessionKey);
        String doableEncrypted = encryptionManager.encryptData(Doable, sessionKey);
        String goalEncrypted = encryptionManager.encryptData(Goal, sessionKey);

        ComfortZoneEntry comfortZoneEntry = new ComfortZoneEntry(sessionManager.getUserID(),
                sessionManager.getUsername(),
                encryptedSessionKey,
                goalEncrypted,
                comfortableEncrypted,
                doableEncrypted);

        ComfortZoneRepository.getInstance().addComfortZoneEntry(comfortZoneEntry, onSuccess, onFailure);

    }

    public void decryptComfortZoneEntry(ComfortZoneEntry comfortZoneEntry) throws Exception{


        String decryptedSessionKey = this.encryptionManager.decryptSessionKey(comfortZoneEntry.getEncryptedSessionKey());
        String decryptedTasksComfortable = this.encryptionManager.decryptData(comfortZoneEntry.getTasksComfortable(), decryptedSessionKey);
        String decryptedTasksDoable = this.encryptionManager.decryptData(comfortZoneEntry.getTasksDoable(), decryptedSessionKey);
        String decryptedTasksGoal = this.encryptionManager.decryptData(comfortZoneEntry.getTasksGoal(), decryptedSessionKey);

        comfortZoneEntry.setTasksComfortable(decryptedTasksComfortable);
        comfortZoneEntry.setTasksDoable(decryptedTasksDoable);
        comfortZoneEntry.setTasksGoal(decryptedTasksGoal);
    }

    public void updateComfortZoneEntry(String encryptedSessionKey,
                                       String documentId,
                                       String tasksComfortable,
                                       String tasksDoable,
                                       String tasksGoal,
                                       OnSuccessListener<String> onSuccess,
                                       OnFailureListener onFailure) throws Exception{

        String decryptedSessionKey = this.encryptionManager.decryptSessionKey(encryptedSessionKey);

        String encryptedTasksComfortable = this.encryptionManager.encryptData(tasksComfortable, decryptedSessionKey);
        String encryptedTasksDoable= this.encryptionManager.encryptData(tasksDoable, decryptedSessionKey);
        String encryptedTasksGoal = this.encryptionManager.encryptData(tasksGoal, decryptedSessionKey);


        ComfortZoneRepository.getInstance().updateComfortZoneEntry(documentId,
                encryptedTasksGoal, encryptedTasksComfortable, encryptedTasksDoable, onSuccess, onFailure);
    }

    public void delete(String documentID, OnSuccessListener<String> onSuccess, OnFailureListener onFailure) {
        ComfortZoneRepository.getInstance().deleteComfortZoneEntry(documentID, onSuccess, onFailure);
    }
}
