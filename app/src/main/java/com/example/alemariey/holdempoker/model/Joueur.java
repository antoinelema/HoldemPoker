package com.example.alemariey.holdempoker.model;

import java.io.Serializable;

/**
 * Created by a.lemariey on 17/07/2018.
 */
public class Joueur implements Serializable{
    private String pseudo;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return pseudo;
    }
}
