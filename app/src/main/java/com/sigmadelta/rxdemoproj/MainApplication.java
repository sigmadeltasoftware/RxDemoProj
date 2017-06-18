package com.sigmadelta.rxdemoproj;

import android.app.Application;

import timber.log.Timber;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initiate Timber
        if (BuildConfig.DEBUG) {
            if (Timber.treeCount() == 0) {
                Timber.plant(new Timber.DebugTree());
            } else if (Timber.treeCount() > 1) {
                Timber.uprootAll();
                Timber.plant(new Timber.DebugTree());
            }
            Timber.d("DebugTree planted!");
        }
    }
}
