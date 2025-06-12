package com.example.catclinic.controllers;

import android.content.Context;

import com.example.catclinic.models.JudgementDayEntry;
import com.example.catclinic.repositories.JudgementDayRepository;
import com.example.catclinic.services.EncryptionManager;
import com.example.catclinic.services.SessionManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JudgementDayHistoryController {
    private final JudgementDayRepository repo;
    private final SessionManager session;
    private final EncryptionManager encryption;
    private ListenerRegistration registration;

    private final JudgementDayController controller;

    public JudgementDayHistoryController(Context context
    ) {
        this.repo = JudgementDayRepository.getInstance();
        this.session = new SessionManager(context);
        this.encryption = new EncryptionManager(context);
        this.controller = new JudgementDayController(context);
    }

    public void startListening(Consumer<List<JudgementDayEntry>> onData,
                               Consumer<Exception> onError) {
        String userId = session.getUserID();
        registration = repo.listenToEntries(userId, (snapshots, err) -> {
            if (err != null) {
                onError.accept(err);
            } else {
                List<JudgementDayEntry> out = new ArrayList<>();
                for (DocumentSnapshot doc: snapshots.getDocuments()) {
                    JudgementDayEntry e = doc.toObject(JudgementDayEntry.class);
                    if (e != null) {
                        e.setDocumentID(doc.getId());
                        try {
                            controller.decrypt(e);
                            out.add(e);
                        } catch(Exception dex) {
                            // optionally report per‐entry failures
                        }
                    }
                }
                onData.accept(out);
            }
        });
    }

    public void stopListening() {
        repo.stopListening(registration);
    }
}
