package com.example.alemariey.holdempoker.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.alemariey.holdempoker.exceptions.CouleureException;
import com.example.alemariey.holdempoker.outils.Outils;
import com.example.dbn.myapplication.JeuxActivity;

import java.text.Normalizer;

/**
 * Created by a.lemariey on 18/07/2018.
 */
public class Carte {
    private static final String PIQUE = "PIQUE";
    private static final String TREFLE = "TREFLE";
    private static final String COEUR = "COEUR";
    private static final String CARREAU = "CARREAU";


    private String couleur;
    private int poid;
    private String img;

    public Carte( String couleur, int poid) throws CouleureException {

        try {
            this.couleur = verifCouleur(couleur);
        } catch (CouleureException e) {
            throw new CouleureException(e.getMessage());
        }
        this.poid = poid;
        if (poid == 14){
            this.img = couleur+1;
        }else{
            this.img=couleur+poid;
        }

    }

    private String verifCouleur(String couleur)throws CouleureException {
//        couleur = Normalizer.normalize(couleur, Normalizer.Form.NFD);
//        couleur = (couleur.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")).toUpperCase();

        if (couleur != PIQUE && couleur != CARREAU && couleur != COEUR && couleur != TREFLE  ){
            throw new CouleureException("Carte invalide : la couleur doit etre [PIQUE,TREFLE,COEUR,CARREAU]");
        }
        return couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getPoid() {
        return poid;
    }

    public String getImg() {
        return img.toLowerCase();
    }

    @Override
    public String toString() {
        return "Carte{" +
                "couleur='" + couleur + '\'' +
                ", poid=" + poid +
                ", img='" + img + '\'' +
                '}';
    }
}
