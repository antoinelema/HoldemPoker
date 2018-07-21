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

    public static void makeSimpleDialogue(Context context,String titre,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //@ TODO Add your code for the button here.
            }
        });
// Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_web);
        alertDialog.show();
    }



}
