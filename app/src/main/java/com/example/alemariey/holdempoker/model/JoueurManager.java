package com.example.alemariey.holdempoker.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alemariey.holdempoker.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class JoueurManager {
    private final static String TAG = "JoueurManager";
    private final static int NBJETON_DEPART = 1000;

    /**
     * envoi une reque pour recupé tout les joueur de la bdd
     * @return liste de joueur
     */
    public static List<Joueur> getAll() {
        List<Joueur> lJoueurs = new ArrayList<>();
        Map<String, Integer> tblJoueur = new HashMap<>();

        // Ouvre une connexion BDD
        JoueurSQLite conn = JoueurSQLite.getInstance(MyApplication.getAppContext());
        SQLiteDatabase bdd = conn.getReadableDatabase();

        String sql = "SELECT " + JoueurSQLite.COL_PSEUDO + " as _pseudo,"
                + JoueurSQLite.COL_JETONS + " FROM " + JoueurSQLite.TABLE_JOUEURS;

        // Lance la requête SQL
        Cursor c = bdd.rawQuery(sql, null);

        // Créer le tableau des monnaies
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    int jeton = c.getInt(c.getColumnIndex(JoueurSQLite.COL_JETONS));
                    Log.d(TAG,jeton+"");

                    String pseudo = c.getString(0);
                    tblJoueur.put(pseudo,jeton);

                    Log.e("DEBUG", "Pseudo : " + pseudo + "  nbjetons " + jeton);
                } while (c.moveToNext());
            }
        }
        // Ferme la connexion BDD
        bdd.close();

        //transforme le hasmap en list
        for (Map.Entry<String, Integer> entry : tblJoueur.entrySet()){
            Joueur joueur = new Joueur(entry.getKey(),entry.getValue());
            lJoueurs.add(joueur);
        }

        return lJoueurs;
    }

    /**
     * insert un joueur dans la bdd et lui attribue 1000 jetons
     * @param pseudo
     */
    public static void insertJoueur(String pseudo){

//        ouvre la bdd
        JoueurSQLite conn = JoueurSQLite.getInstance(MyApplication.getAppContext());
        SQLiteDatabase bdd = conn.getWritableDatabase();

        Log.i(TAG, "insert Joueur :"+pseudo);
        String insertSql = "INSERT INTO " + JoueurSQLite.TABLE_JOUEURS + " (pseudo,nbJetons) VALUES ('" + pseudo + "',"+NBJETON_DEPART+");";

        bdd.execSQL(insertSql);

        //ferme la bdd
        bdd.close();
    }

    /**
     * update un joueur dans la bdd
     * @param joueur
     */
    public static void upDateJoueur(Joueur joueur){
        //ouvre la bdd
        JoueurSQLite conn = JoueurSQLite.getInstance(MyApplication.getAppContext());
        SQLiteDatabase bdd = conn.getWritableDatabase();

        Log.i(TAG, "update Joueur :"+joueur.getPseudo());
        String updateSql ="UPDATE "+ JoueurSQLite.TABLE_JOUEURS + " SET "+JoueurSQLite.COL_JETONS + " = '"+ joueur.getNbJeton() +"' WHERE "+JoueurSQLite.COL_PSEUDO + " = '"+ joueur.getPseudo() +"';";

        bdd.execSQL(updateSql);

        //ferme la bdd
        bdd.close();

    }


}
