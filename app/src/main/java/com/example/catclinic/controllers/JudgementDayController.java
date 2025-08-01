package com.example.catclinic.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.EncryptionManager;
import com.example.catclinic.services.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class JudgementDayController {

    private final SessionManager sessionManager;
    private final EncryptionManager encryptionManager;

    public JudgementDayController(Context context){
        this.sessionManager = new SessionManager(context);
        this.encryptionManager = new EncryptionManager(context);
    }

    public void createJudgmentDayEntry(String thoughtOnTrial,
                                       String evidenceFor,
                                       String evidenceAgainst,
                                       String finalVerdict,
                                       OnSuccessListener<JudgementDayEntry> onSuccess,
                                       OnFailureListener onFailure) throws Exception {

        if(thoughtOnTrial.isEmpty())
        {
            onFailure.onFailure(new Exception("The thought on trial cannot be empty"));
            return;
        }

        if(evidenceFor.isEmpty() && evidenceAgainst.isEmpty()){
            onFailure.onFailure(new Exception("Evidence for both sides cannot be empty"));
            return;
        }

        if(finalVerdict.isEmpty()){
            onFailure.onFailure(new Exception("Final verdict cannot be empty"));
            return;
        }

        //get the username and the userID from the sessionManager
        String userId = sessionManager.getUserID();
        String userName = sessionManager.getUsername();

        //create the encryption keys
        String sessionKey = EncryptionManager.generateSessionKey();


        String encryptedSessionKey = sessionKey;

        try {
            encryptedSessionKey = encryptionManager.encryptSessionKey(sessionKey);
        } catch (Exception e) {
            Log.e("JudgementDayController", "encryptSessionKey failed", e);
            onFailure.onFailure(new Exception("session Key encryption failed"));
            return;
        }


        String encrypted_thoughtOnTrial = encryptionManager.encryptData(thoughtOnTrial, sessionKey);
        String encrypted_evidenceFor = encryptionManager.encryptData(evidenceFor, sessionKey);
        String encrypted_evidenceAgainst = encryptionManager.encryptData(evidenceAgainst, sessionKey);
        String encrypted_finalVerdict = encryptionManager.encryptData(finalVerdict, sessionKey);



        JudgementDayEntry judgementDayEntry = new JudgementDayEntry(userId,
                                            userName, encrypted_thoughtOnTrial,
                                            encrypted_evidenceFor, encrypted_evidenceAgainst,
                                            encrypted_finalVerdict, encryptedSessionKey);

        JudgementDayRepository.getInstance().addJudgementDayEntry(judgementDayEntry, onSuccess, onFailure);

    }

    public void decrypt(JudgementDayEntry judgementDayEntry) throws Exception {

        String decrypted_sessionKey = encryptionManager.decryptSessionKey(judgementDayEntry.getEncryptedSessionKey());
        String decrypted_thoughtOnTrial = encryptionManager.decryptData(judgementDayEntry.getThoughtOnTrial(), decrypted_sessionKey);
        String decrypted_evidenceFor = encryptionManager.decryptData(judgementDayEntry.getEvidenceFor(), decrypted_sessionKey);
        String decrypted_evidenceAgainst = encryptionManager.decryptData(judgementDayEntry.getEvidenceAgainst(), decrypted_sessionKey);
        String decrypted_finalVerdict = encryptionManager.decryptData(judgementDayEntry.getFinalVerdict(), decrypted_sessionKey);

        //if there is an io error till here then the function would throw it and not go further
        judgementDayEntry.setThoughtOnTrial(decrypted_thoughtOnTrial);
        judgementDayEntry.setEvidenceFor(decrypted_evidenceFor);
        judgementDayEntry.setEvidenceAgainst(decrypted_evidenceAgainst);
        judgementDayEntry.setFinalVerdict(decrypted_finalVerdict);
    }



    public void update(String encryptedSessionKey,
                       String documentID,
                       String thoughtOnTrialEdit,
                       String evidenceForEdit,
                       String evidenceAgainstEdit,
                       String finalVerdictEdit,
                       OnSuccessListener<Void> onSuccess, OnFailureListener onFailure) throws Exception{

        String decrypted_sessionKey = encryptionManager.decryptSessionKey(encryptedSessionKey);

        String encrypted_thoughtOnTrialEdit = encryptionManager.encryptData(thoughtOnTrialEdit, decrypted_sessionKey);
        String encrypted_evidenceForEdit = encryptionManager.encryptData(evidenceForEdit, decrypted_sessionKey);
        String encrypted_evidenceAgainstEdit = encryptionManager.encryptData(evidenceAgainstEdit, decrypted_sessionKey);
        String encrypted_finalVerdictEdit = encryptionManager.encryptData(finalVerdictEdit, decrypted_sessionKey);



        JudgementDayRepository.getInstance().updateEntry(documentID,
                encrypted_thoughtOnTrialEdit,
                encrypted_evidenceForEdit,
                encrypted_evidenceAgainstEdit,
                encrypted_finalVerdictEdit,
                onSuccess,
                onFailure);
    }

    public void delete(String documentID, OnSuccessListener<Void> onSuccess, OnFailureListener onFailure)
    {
        JudgementDayRepository.getInstance().deleteEntry(documentID, onSuccess,onFailure);
    }



}
