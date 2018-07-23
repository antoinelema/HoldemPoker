package com.example.alemariey.holdempoker;

import android.app.Application;
import android.content.Context;

import static java.lang.System.exit;

/**
 * Created by a.lemariey on 23/07/2018.
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static void quit(){

       exit(1);// ferme l'activité
    }
}
