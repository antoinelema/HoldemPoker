package com.example.alemariey.holdempoker.outils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dbn on 11/04/2017.
 */

public class Outils {
    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Chargement des polices à utiliser sur des TextViews
     */
    public static void chargePolices(Context context, TextView textView) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "RioGrande.ttf");
        textView.setTypeface(font);
    }

}
