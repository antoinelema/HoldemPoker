package com.example.alemariey.holdempoker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class JoueurManager {
    static Joueur antoine = new Joueur("Antoine"); //
    static Joueur paul = new Joueur("Paul"); //
    static Map<String, Joueur> tblJoueur = new HashMap<String, Joueur>();

    static {
        tblJoueur.put("1",antoine);
        tblJoueur.put("2",paul);
    }

    public static Map<String,Joueur> getTblJoueur(){
        return tblJoueur;
    }
}
