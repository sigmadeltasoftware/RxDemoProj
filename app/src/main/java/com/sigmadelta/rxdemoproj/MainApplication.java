package com.sigmadelta.rxdemoproj;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;


public class MainApplication extends Application {

    private static Context _ctx;

    @Override
    public void onCreate() {
        super.onCreate();

        _ctx = getApplicationContext();

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

    // TODO: Check validity of this method
    public static Context getContext() {
        return _ctx;
    }
}
