package com.example.alemariey.holdempoker.outils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alemariey.holdempoker.R;

/**
 * Created by dbn on 11/04/2017.
 */

public class Outils {

    /**
     * Chargement des polices à utiliser sur des TextViews
     */
    public static void chargePolices(Context context, TextView textView,String font) {
        Typeface typefont = Typeface.createFromAsset(context.getAssets(),
                font);
        textView.setTypeface(typefont);
    }

    /**
     * affiche un toast avec le texte passé en parametre
     * @param context
     * @param text
     */
    public static void makeToast(Context context,String text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }





}
