package com.example.alemariey.holdempoker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class Joueur implements Serializable{
    private String pseudo;
    private List<Carte> main;
    private boolean fold;
    private int point;
    private String combinaison;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.main = new ArrayList<>();
        this.fold = false;
        this.point = 0;
        this.combinaison = "";
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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
        this.point = point;
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
        int comparPoid = ((Joueur)compareJoueur).getPoint();

        return comparPoid-this.getPoint();
    }
}
