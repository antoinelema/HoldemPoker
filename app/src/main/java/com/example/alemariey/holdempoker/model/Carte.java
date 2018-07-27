package com.example.alemariey.holdempoker.model;

import android.view.View;
import android.widget.ImageView;

import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.exceptions.CouleureException;
import com.example.dbn.myapplication.JeuxActivity;

/**
 * Created by a.lemariey on 18/07/2018.
 * class d'objet carte
 *
 */
public class Carte implements  Comparable<Carte>{
    private static final String PIQUE = "PIQUE";
    private static final String TREFLE = "TREFLE";
    private static final String COEUR = "COEUR";
    private static final String CARREAU = "CARREAU";


    private String couleur; //couleur de la carte (pique trefle coeur ou carreau)
    private int poid;// valeur de la carte de 2 à 14 (11 12 13 14 pour valet dame roi as)
    private String imgName; //nom de l'image de la carte
    private ImageView vueImage; //vue de la carte

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

    /**
     * verification que la couleur de la carte existe
     * @param couleur
     * @return la couleur
     * @throws CouleureException
     */
    private String verifCouleur(String couleur)throws CouleureException {

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

    /**
     * compare 2 carte pour les ranger dans l'ordre decroissant de poids
     * @param compareCarte
     * @return
     */
    @Override
    public int compareTo(Carte compareCarte) {
        int comparPoid = ((Carte)compareCarte).getPoid();

        return comparPoid-this.getPoid();
    }
}
