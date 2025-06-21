package com.example.catclinic.controllers;

import android.content.Context;

import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.repositories.ComfortZoneRepository;
import com.example.catclinic.services.EncryptionManager;
import com.example.catclinic.services.SessionManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComfortZoneHistoryController{
    private final ComfortZoneRepository repo;
    private final SessionManager session;
    private final EncryptionManager encryption;
    private ListenerRegistration registration;

    private final ComfortZoneController controller;

    public ComfortZoneHistoryController(Context context
    ) {
        this.repo = ComfortZoneRepository.getInstance();
        this.session = new SessionManager(context);
        this.encryption = new EncryptionManager(context);
        this.controller = new ComfortZoneController(context);
    }

    public void startListening(Consumer<List<ComfortZoneEntry>> onData,
                               Consumer<Exception> onError) {
        String userId = session.getUserID();
        registration = repo.listenToEntries(userId, (snapshots, err) -> {
            if (err != null) {
                onError.accept(err);
            } else {
                List<ComfortZoneEntry> out = new ArrayList<>();
                for (DocumentSnapshot doc: snapshots.getDocuments()) {
                    ComfortZoneEntry e = doc.toObject(ComfortZoneEntry.class);
                    if (e != null) {
                        e.setDocumentID(doc.getId());
                        try {
                            controller.decryptComfortZoneEntry(e);
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
