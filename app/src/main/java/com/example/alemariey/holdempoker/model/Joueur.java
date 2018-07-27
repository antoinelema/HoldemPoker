package com.example.alemariey.holdempoker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class Joueur implements Serializable{
    private String pseudo;// nom du joueur
    private int nbJeton;// jetons du joueur
    private List<Carte> main; //cartes dans la main du joueur
    private boolean fold; //si le joueur est couché ou non
    private int point; //point en fint de partie du joueur
    private String combinaison; //combinaison (paire brelan quint ...) du joueur en fin de partie

    public Joueur(String pseudo, int nbJeton) {
        this.pseudo = pseudo;
        this.nbJeton = nbJeton;
        this.main = new ArrayList<>();
        this.fold = false;
        this.point = 0;
        this.combinaison = "";
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getNbJeton() {
        return nbJeton;
    }

    public void addJeton(int nbJeton) {
        this.nbJeton += nbJeton;
    }

    public void subJeton(int nbJeton) {
        this.nbJeton -= nbJeton;
    }

    public List<Carte> getMain() {
        return main;
    }

    public boolean isFold() {
        return fold;
    }

    public void fold() {
        this.fold = true;
    }

    public void addCarteInMain(Carte carte) {
        this.main.add(carte);
    }

    public int getPoint() {
        return point;
    }

    public String getCombinaison() {
        return combinaison;
    }

    public void setCombinaison(String combinaison) {
        this.combinaison = combinaison;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public void reset() {
        this.main.clear();
        this.fold = false;
        this.point = 0;
    }

    @Override
    public String toString() {
        return pseudo;
    }

    public int compareTo(Joueur compareJoueur) {
        int comparPoints = ((Joueur)compareJoueur).getPoint();

        return comparPoints-this.getPoint();
    }
}
