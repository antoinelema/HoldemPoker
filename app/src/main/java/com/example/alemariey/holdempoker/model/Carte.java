package com.example.alemariey.holdempoker.model;

import android.view.View;
import android.widget.ImageView;

import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.exceptions.CouleureException;

/**
 * Created by a.lemariey on 18/07/2018.
 */
public class Carte implements  Comparable<Carte>{
    private static final String PIQUE = "PIQUE";
    private static final String TREFLE = "TREFLE";
    private static final String COEUR = "COEUR";
    private static final String CARREAU = "CARREAU";


    private String couleur;
    private int poid;
    private String imgName;
    private ImageView vueImage;

    public Carte( String couleur, int poid) throws CouleureException {

        try {
            this.couleur = verifCouleur(couleur);
        } catch (CouleureException e) {
            throw new CouleureException(e.getMessage());
        }
        this.poid = poid;
        if (poid == 14){
            this.imgName = couleur+1;
        }else{
            this.imgName = couleur+poid;
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

    public String getImgName() {
        return imgName.toLowerCase();
    }

    public void setVueImage(ImageView vueCarte){

        this.vueImage = vueCarte;
    }

    public ImageView getVueImage() {
        return vueImage;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "couleur='" + couleur + '\'' +
                ", poid=" + poid +
                ", imgName='" + imgName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Carte compareCarte) {
        int comparPoid = ((Carte)compareCarte).getPoid();

        return comparPoid-this.getPoid();
    }
}
