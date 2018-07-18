package com.example.alemariey.holdempoker.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a.lemariey on 18/07/2018.
 */
public class Deck {
    private static String TAG = "DECK";
    private static final String P = "PIQUE";
    private static final String T = "TREFLE";
    private static final String CO = "COEUR";
    private static final String CA = "CARREAU";

    private static List<Carte> deck = new ArrayList<>();

    static {
        try {
            Carte asPique = new Carte(P,14); //attention l'as a un poid de 14
            deck.add(asPique);
            Carte deuxPique = new Carte(P,2);
            deck.add(deuxPique);
            Carte troisPique = new Carte(P,3);
            deck.add(troisPique);
            Carte quatrePique = new Carte(P,4);
            deck.add(quatrePique);
            Carte cinqPique = new Carte(P,5);
            deck.add(cinqPique);
            Carte sixPique = new Carte(P,6);
            deck.add(sixPique);
            Carte septPique = new Carte(P,7);
            deck.add(septPique);
            Carte huitPique = new Carte(P,8);
            deck.add(huitPique);
            Carte neufPique = new Carte(P,9);
            deck.add(neufPique);
            Carte dixPique = new Carte(P,10);
            deck.add(dixPique);
            Carte valetPique = new Carte(P,11);
            deck.add(valetPique);
            Carte damePique = new Carte(P,12);
            deck.add(damePique);
            Carte roiPique = new Carte(P,13);
            deck.add(roiPique);

            Carte asTrefle = new Carte(T,14);
            deck.add(asTrefle);
            Carte deuxTrefle = new Carte(T,2);
            deck.add(deuxTrefle);
            Carte troisTrefle = new Carte(T,3);
            deck.add(troisTrefle);
            Carte quatreTrefle = new Carte(T,4);
            deck.add(quatreTrefle);
            Carte cinqTrefle = new Carte(T,5);
            deck.add(cinqTrefle);
            Carte sixTrefle = new Carte(T,6);
            deck.add(sixTrefle);
            Carte septTrefle = new Carte(T,7);
            deck.add(septTrefle);
            Carte huitTrefle = new Carte(T,8);
            deck.add(huitTrefle);
            Carte neufTrefle = new Carte(T,9);
            deck.add(neufTrefle);
            Carte dixTrefle = new Carte(T,10);
            deck.add(dixTrefle);
            Carte valetTrefle = new Carte(T,11);
            deck.add(valetTrefle);
            Carte dameTrefle = new Carte(T,12);
            deck.add(dameTrefle);
            Carte roiTrefle = new Carte(T,13);
            deck.add(roiTrefle);

            Carte asCoeur = new Carte(CO,14);
            deck.add(asCoeur);
            Carte deuxCoeur = new Carte(CO,2);
            deck.add(deuxCoeur);
            Carte troisCoeur = new Carte(CO,3);
            deck.add(troisCoeur);
            Carte quatreCoeur = new Carte(CO,4);
            deck.add(quatreCoeur);
            Carte cinqCoeur = new Carte(CO,5);
            deck.add(cinqCoeur);
            Carte sixCoeur = new Carte(CO,6);
            deck.add(sixCoeur);
            Carte septCoeur = new Carte(CO,7);
            deck.add(septCoeur);
            Carte huitCoeur = new Carte(CO,8);
            deck.add(huitCoeur);
            Carte neufCoeur = new Carte(CO,9);
            deck.add(neufCoeur);
            Carte dixCoeur = new Carte(CO,10);
            deck.add(dixCoeur);
            Carte valetCoeur = new Carte(CO,11);
            deck.add(valetCoeur);
            Carte dameCoeur = new Carte(CO,12);
            deck.add(dameCoeur);
            Carte roiCoeur = new Carte(CO,13);
            deck.add(roiCoeur);

            Carte asCarreau = new Carte(CA,14);
            deck.add(asCarreau);
            Carte deuxCarreau = new Carte(CA,2);
            deck.add(deuxCarreau);
            Carte troisCarreau = new Carte(CA,3);
            deck.add(troisCarreau);
            Carte quatreCarreau = new Carte(CA,4);
            deck.add(quatreCarreau);
            Carte cinqCarreau = new Carte(CA,5);
            deck.add(cinqCarreau);
            Carte sixCarreau = new Carte(CA,6);
            deck.add(sixCarreau);
            Carte septCarreau = new Carte(CA,7);
            deck.add(septCarreau);
            Carte huitCarreau = new Carte(CA,8);
            deck.add(huitCarreau);
            Carte neufCarreau = new Carte(CA,9);
            deck.add(neufCarreau);
            Carte dixCarreau = new Carte(CA,10);
            deck.add(dixCarreau);
            Carte valetCarreau = new Carte(CA,11);
            deck.add(valetCarreau);
            Carte dameCarreau = new Carte(CA,12);
            deck.add(dameCarreau);
            Carte roicCarreau = new Carte(CA,13);
            deck.add(roicCarreau);

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

    }

    public static List<Carte> getDeck() {
        Log.d(TAG, deck.get(1).getImg());
        return deck;
    }
}
