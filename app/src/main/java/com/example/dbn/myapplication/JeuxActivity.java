package com.example.dbn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.model.Carte;
import com.example.alemariey.holdempoker.model.Deck;
import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.outils.Outils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class JeuxActivity extends AppCompatActivity {
    private String TAG = "JEUX";
    private boolean jeux = true;
    private String action = "initial";
    private Joueur joueur;
    private List<Carte>deck = new ArrayList<>(Deck.getDeck());
    private List<ImageView> listCarteAffiche = new ArrayList<>();
    
    private int indexCarte;
    private int buttonBetPressed = 0;
    
    private ImageView cardMain1;
    private ImageView cardMain2;
    private ImageView cardImage1;
    private ImageView cardImage2;
    private ImageView cardImage3;
    private ImageView cardImage4;
    private ImageView cardImage5;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activité jeux");
        indexCarte = 0;

        setContentView(R.layout.activity_jeux);

        listCarteAffiche.add(cardMain1);
        listCarteAffiche.add(cardMain2);
        listCarteAffiche.add(cardImage1);
        listCarteAffiche.add(cardImage2);
        listCarteAffiche.add(cardImage3);
        listCarteAffiche.add(cardImage4);
        listCarteAffiche.add(cardImage5);

        for (Carte carte: deck){
            Log.d(TAG, carte.getImg());
        }

        getJoueur();//recupere l'objet joueur de l'intent
        Button buttonBet = (Button) findViewById(R.id.buttonBet);
        deroulementPartie();//deroulement de la partie

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jeux, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//------------------------------------ Fonctions applicative ---------------------------------------
    private void getJoueur() {
        Intent intent = getIntent();
        joueur = (Joueur)intent.getSerializableExtra("joueur");
        afficheNomJoueur();
    }

    private void afficheNomJoueur(){
        TextView nomJoueur = (TextView)findViewById(R.id.txtNomJoueur);
        nomJoueur.setText(joueur.getPseudo());
    }



    private void deroulementPartie(){

        switch (action){
            case "initial":
                melangeDeck();
                afficheMain();
                break;
            case "flop":
                flop();
                break;
            case "turn":
                turn();
                break;
            case "river":
                river();
                break;
            case "fin":
                determineGagant();
                buttonBetPressed=0;
                deroulementPartie();
        }



    }




    /**
     * melange le deck de cartes
     */
    private void  melangeDeck(){
        indexCarte = 0; //on remet l'index a 0 à chaque melange de carte
        Collections.shuffle(deck);
    }

    private void afficheMain(){
        cardMain1   = (ImageView)findViewById(R.id.cardMain1);
        retourneCarte(cardMain1);
        cardMain2   = (ImageView)findViewById(R.id.cardMain2);
        retourneCarte(cardMain2);
    }

    /**
     * action du flop
     */
    private void flop (){
        cardImage1   = (ImageView)findViewById(R.id.cardImage1);
        retourneCarte(cardImage1);
        cardImage2   = (ImageView)findViewById(R.id.cardImage2);
        retourneCarte(cardImage2);
        cardImage3   = (ImageView)findViewById(R.id.cardImage3);
        retourneCarte(cardImage3);
    }

    /**
     * action du turn
     */
    private void turn (){
        cardImage4   = (ImageView)findViewById(R.id.cardImage4);
        retourneCarte(cardImage4);
    }

    /**
     * action de la river
     */
    private void river (){
        cardImage5   = (ImageView)findViewById(R.id.cardImage5);
        retourneCarte(cardImage5);
    }

    /**
     * retourne la prochaine carte du deck
     * @param carte
     */
    private void retourneCarte(ImageView carte){
        Carte faceCarte = deck.get(indexCarte);
        int identifier = getResources().getIdentifier(faceCarte.getImg(), "drawable", getPackageName());//transforme le string en drawable

        carte.setImageResource(identifier);
        carte.getLayoutParams().height=476;
        carte.getLayoutParams().width=314;
        indexCarte ++;
    }

    public void afficheDos(ImageView carte){
        carte.setImageResource(R.drawable.dos);
    }
    
    public void clique_bet(View v){
        buttonBetPressed++;
        Outils.makeToast(this,buttonBetPressed+"bet");
        if (buttonBetPressed == 1){
            action = "flop";
            deroulementPartie();
        }else if (buttonBetPressed == 2){
            action = "turn";
            deroulementPartie();
        }else if (buttonBetPressed == 3){
            action="river";
            deroulementPartie();
        }else if (buttonBetPressed == 4){
            action="fin";
            deroulementPartie();
        }
    }
    
    
    
    public void clique_fold(View v){

    }

    private void determineGagant() {

        for (ImageView viewCarte: listCarteAffiche){
            afficheDos(viewCarte);
        }

        action = "initial";
    }
}
