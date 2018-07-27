package com.example.alemariey.holdempoker;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Created by a.lemariey on 23/07/2018.
 */
public class MyApplication extends Application {
    private static final String ANDERSON = "Anderson.ttf";
    private static final String RIOGRANDE = "RioGrande.ttf";
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }


    public static String getANDERSON() {
        return ANDERSON;
    }

    public static String getRIOGRANDE() {
        return RIOGRANDE;
    }
}
