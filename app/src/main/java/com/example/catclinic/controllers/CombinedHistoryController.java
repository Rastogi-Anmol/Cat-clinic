package com.example.catclinic.controllers;

import android.content.Context;

import com.example.catclinic.models.CombinedHistoryModel;
import com.example.catclinic.models.ComfortZoneEntry;
import com.example.catclinic.models.JudgementDayEntry;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class CombinedHistoryController {
    private final ComfortZoneHistoryController czController;
    private final JudgementDayHistoryController jdController;

    // hold the latest batch from each source
    private List<ComfortZoneEntry> czEntries = new ArrayList<>();
    private List<JudgementDayEntry> jdEntries = new ArrayList<>();

    // callbacks to the caller
    private Consumer<List<CombinedHistoryModel>> onData;
    private Consumer<Exception> onError;

    public CombinedHistoryController(Context ctx) {
        czController = new ComfortZoneHistoryController(ctx);
        jdController = new JudgementDayHistoryController(ctx);
    }

    /**
     * Start listening to both collections.
     * Every time one updates, we rebuild and re-emit the merged list.
     */
    public void startListening(Consumer<List<CombinedHistoryModel>> onData,
                               Consumer<Exception> onError) {
        this.onData  = onData;
        this.onError = onError;

        czController.startListening(
                czList -> {
                    this.czEntries = czList;
                    emitCombined();
                },
                onError
        );

        jdController.startListening(
                jdList -> {
                    this.jdEntries = jdList;
                    emitCombined();
                },
                onError
        );
    }

    public void stopListening() {
        czController.stopListening();
        jdController.stopListening();
    }

    /** Map + merge + (optional) sort + emit */
    private void emitCombined() {
        List<CombinedHistoryModel> merged = new ArrayList<>();

        // prepare your formatters once
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


        for (ComfortZoneEntry e : czEntries) {
            Timestamp ts = e.getPostingTime();
            Date d  = ts.toDate();
            String  date = dateFmt.format(d);
            String  time = timeFmt.format(d);

            merged.add(new CombinedHistoryModel(
                    "Goal",
                    e.getDocumentID(),
                    e.getTasksGoal(),
                    date,
                    time
            ));
        }


        for (JudgementDayEntry e : jdEntries) {
            Timestamp ts = e.getPostingTime();
            Date    d  = ts.toDate();
            String  date = dateFmt.format(d);
            String  time = timeFmt.format(d);

            merged.add(new CombinedHistoryModel(
                    "Thought on Trial",
                    e.getDocumentID(),
                    e.getThoughtOnTrial(),    // or e.getThoughtOnTrial()
                    date,
                    time
            ));
        }

        Collections.sort(
                merged,
                // explicitly tell it: we’re comparing HistoryWrapperModel → String
                Comparator.<CombinedHistoryModel,String>comparing(
                        w -> w.getDate() + " " + w.getTime()
                ).reversed()
        );


        // 4) emit
        onData.accept(merged);
    }
}
