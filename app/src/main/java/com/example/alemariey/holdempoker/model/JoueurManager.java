package com.example.alemariey.holdempoker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class JoueurManager {
    static Joueur antoine = new Joueur("Antoine"); //
    static Joueur paul = new Joueur("Paul"); //
    static Map<String, String> tblJoueur = new HashMap<String, String>();

    static {
        tblJoueur.put("1",antoine.getPseudo());
        tblJoueur.put("2",paul.getPseudo());
    }

    public static Map<String,String> getTblJoueur(){
        return tblJoueur;
    }
}
