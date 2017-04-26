package com.github.w_kamil.shoppingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyStethoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        boolean isDebug = BuildConfig.DEBUG;
        Stetho.initializeWithDefaults(this);
    }
}
