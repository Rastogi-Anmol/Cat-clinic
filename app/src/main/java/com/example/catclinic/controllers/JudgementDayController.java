package com.example.catclinic.controllers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JudgementDayController {

    private SessionManager sessionManager;

    public JudgementDayController(Context context){
        this.sessionManager = new SessionManager(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createJudgmentDayEntry(String thoughtOnTrial,
                                       String evidenceFor,
                                       String evidenceAgainst,
                                       String finalVerdict,
                                       OnSuccessListener<JudgementDayEntry> onSuccess,
                                       OnFailureListener onFailure){

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

        //get the
        String userId = sessionManager.getUserID();
        String userName = sessionManager.getUsername();

        JudgementDayEntry judgementDayEntry = new JudgementDayEntry(userId,
                                            userName, thoughtOnTrial,
                                            evidenceFor, evidenceAgainst,
                                            finalVerdict);

        JudgementDayRepository.getInstance().addJudgementDayEntry(judgementDayEntry, onSuccess, onFailure);

    }

}
