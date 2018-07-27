package com.example.alemariey.holdempoker.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.lemariey on 18/07/2018.
 */
public class Deck {
    private static String TAG = "DECK";
    public static final String PIQUE = "PIQUE";
    public static final String TREFLE = "TREFLE";
    public static final String COEUR = "COEUR";
    public static final String CARREAU = "CARREAU";

    private static List<Carte> deck = new ArrayList<>();

    //creation du deck de 52 cartes
    static {
        try {
            Carte asPique = new Carte(PIQUE,14); //attention l'as a un poid de 14
            deck.add(asPique);
            Carte deuxPique = new Carte(PIQUE,2);
            deck.add(deuxPique);
            Carte troisPique = new Carte(PIQUE,3);
            deck.add(troisPique);
            Carte quatrePique = new Carte(PIQUE,4);
            deck.add(quatrePique);
            Carte cinqPique = new Carte(PIQUE,5);
            deck.add(cinqPique);
            Carte sixPique = new Carte(PIQUE,6);
            deck.add(sixPique);
            Carte septPique = new Carte(PIQUE,7);
            deck.add(septPique);
            Carte huitPique = new Carte(PIQUE,8);
            deck.add(huitPique);
            Carte neufPique = new Carte(PIQUE,9);
            deck.add(neufPique);
            Carte dixPique = new Carte(PIQUE,10);
            deck.add(dixPique);
            Carte valetPique = new Carte(PIQUE,11);
            deck.add(valetPique);
            Carte damePique = new Carte(PIQUE,12);
            deck.add(damePique);
            Carte roiPique = new Carte(PIQUE,13);
            deck.add(roiPique);

            Carte asTrefle = new Carte(TREFLE,14);
            deck.add(asTrefle);
            Carte deuxTrefle = new Carte(TREFLE,2);
            deck.add(deuxTrefle);
            Carte troisTrefle = new Carte(TREFLE,3);
            deck.add(troisTrefle);
            Carte quatreTrefle = new Carte(TREFLE,4);
            deck.add(quatreTrefle);
            Carte cinqTrefle = new Carte(TREFLE,5);
            deck.add(cinqTrefle);
            Carte sixTrefle = new Carte(TREFLE,6);
            deck.add(sixTrefle);
            Carte septTrefle = new Carte(TREFLE,7);
            deck.add(septTrefle);
            Carte huitTrefle = new Carte(TREFLE,8);
            deck.add(huitTrefle);
            Carte neufTrefle = new Carte(TREFLE,9);
            deck.add(neufTrefle);
            Carte dixTrefle = new Carte(TREFLE,10);
            deck.add(dixTrefle);
            Carte valetTrefle = new Carte(TREFLE,11);
            deck.add(valetTrefle);
            Carte dameTrefle = new Carte(TREFLE,12);
            deck.add(dameTrefle);
            Carte roiTrefle = new Carte(TREFLE,13);
            deck.add(roiTrefle);

            Carte asCoeur = new Carte(COEUR,14);
            deck.add(asCoeur);
            Carte deuxCoeur = new Carte(COEUR,2);
            deck.add(deuxCoeur);
            Carte troisCoeur = new Carte(COEUR,3);
            deck.add(troisCoeur);
            Carte quatreCoeur = new Carte(COEUR,4);
            deck.add(quatreCoeur);
            Carte cinqCoeur = new Carte(COEUR,5);
            deck.add(cinqCoeur);
            Carte sixCoeur = new Carte(COEUR,6);
            deck.add(sixCoeur);
            Carte septCoeur = new Carte(COEUR,7);
            deck.add(septCoeur);
            Carte huitCoeur = new Carte(COEUR,8);
            deck.add(huitCoeur);
            Carte neufCoeur = new Carte(COEUR,9);
            deck.add(neufCoeur);
            Carte dixCoeur = new Carte(COEUR,10);
            deck.add(dixCoeur);
            Carte valetCoeur = new Carte(COEUR,11);
            deck.add(valetCoeur);
            Carte dameCoeur = new Carte(COEUR,12);
            deck.add(dameCoeur);
            Carte roiCoeur = new Carte(COEUR,13);
            deck.add(roiCoeur);

            Carte asCarreau = new Carte(CARREAU,14);
            deck.add(asCarreau);
            Carte deuxCarreau = new Carte(CARREAU,2);
            deck.add(deuxCarreau);
            Carte troisCarreau = new Carte(CARREAU,3);
            deck.add(troisCarreau);
            Carte quatreCarreau = new Carte(CARREAU,4);
            deck.add(quatreCarreau);
            Carte cinqCarreau = new Carte(CARREAU,5);
            deck.add(cinqCarreau);
            Carte sixCarreau = new Carte(CARREAU,6);
            deck.add(sixCarreau);
            Carte septCarreau = new Carte(CARREAU,7);
            deck.add(septCarreau);
            Carte huitCarreau = new Carte(CARREAU,8);
            deck.add(huitCarreau);
            Carte neufCarreau = new Carte(CARREAU,9);
            deck.add(neufCarreau);
            Carte dixCarreau = new Carte(CARREAU,10);
            deck.add(dixCarreau);
            Carte valetCarreau = new Carte(CARREAU,11);
            deck.add(valetCarreau);
            Carte dameCarreau = new Carte(CARREAU,12);
            deck.add(dameCarreau);
            Carte roicCarreau = new Carte(CARREAU,13);
            deck.add(roicCarreau);

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

    }

    public static List<Carte> getDeck() {
        Log.d(TAG, deck.get(1).getImgName());
        return deck;
    }
}
