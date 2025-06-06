package com.example.catclinic.controllers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.EncryptionManager;
import com.example.catclinic.services.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

import java.io.IOException;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JudgementDayController {

    private SessionManager sessionManager;
    private EncryptionManager encryptionManager;

    public JudgementDayController(Context context){
        this.sessionManager = new SessionManager(context);
        this.encryptionManager = new EncryptionManager(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createJudgmentDayEntry(String thoughtOnTrial,
                                       String evidenceFor,
                                       String evidenceAgainst,
                                       String finalVerdict,
                                       OnSuccessListener<JudgementDayEntry> onSuccess,
                                       OnFailureListener onFailure) throws IOException {

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

        String encrypted_thoughtOnTrial = encryptionManager.encryptData(thoughtOnTrial);
        String encrypted_evidenceFor = encryptionManager.encryptData(evidenceFor);
        String encrypted_evidenceAgainst = encryptionManager.encryptData(evidenceAgainst);
        String encrypted_finalVerdict = encryptionManager.encryptData(finalVerdict);


        JudgementDayEntry judgementDayEntry = new JudgementDayEntry(userId,
                                            userName, encrypted_thoughtOnTrial,
                                            encrypted_evidenceFor, encrypted_evidenceAgainst,
                                            encrypted_finalVerdict);

        JudgementDayRepository.getInstance().addJudgementDayEntry(judgementDayEntry, onSuccess, onFailure);

    }

    public void decrypt(JudgementDayEntry judgementDayEntry) throws IOException {
        String decrypted_thoughtOnTrial = encryptionManager.decryptData(judgementDayEntry.getThoughtOnTrial());
        String decrypted_evidenceFor = encryptionManager.decryptData(judgementDayEntry.getEvidenceFor());
        String decrypted_evidenceAgainst = encryptionManager.decryptData(judgementDayEntry.getEvidenceAgainst());
        String decrypted_finalVerdict = encryptionManager.decryptData(judgementDayEntry.getFinalVerdict());

        //if there is an io error till here then the function would throw it and not go further
        judgementDayEntry.setThoughtOnTrial(decrypted_thoughtOnTrial);
        judgementDayEntry.setEvidenceFor(decrypted_evidenceFor);
        judgementDayEntry.setEvidenceAgainst(decrypted_evidenceAgainst);
        judgementDayEntry.setFinalVerdict(decrypted_finalVerdict);
    }

}
