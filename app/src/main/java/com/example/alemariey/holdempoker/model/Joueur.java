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

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.main = new ArrayList<>();
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

    public void addCarteInMain(Carte carte) {
        main.add(carte);
    }

    public void viderMain() {
       main.clear();
    }

    @Override
    public String toString() {
        return pseudo;
    }
}
