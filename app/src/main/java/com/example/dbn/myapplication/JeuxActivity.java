package com.example.dbn.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private List<Carte> listCarteCentre = new ArrayList<>();
    private List<Joueur> listJoueur = new ArrayList<>();
    
    private int indexCarte;
    private int buttonPressed = 0;

    private Boolean fold = false;
    
    private ImageView cardMain1;
    private ImageView cardMain2;
//    private ImageView cardImage1;
//    private ImageView cardImage2;
//    private ImageView cardImage3;
//    private ImageView cardImage4;
//    private ImageView cardImage5;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activité jeux");
        indexCarte = 0;

        setContentView(R.layout.activity_jeux);


        getJoueurs();//recupere l'objet joueur de l'intent
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
    private void getJoueurs() {
        Intent intent = getIntent();
        joueur = (Joueur)intent.getSerializableExtra("joueur");
        //TODO ajouter recup nb d'ia et creer joueurs

        listJoueur.add(joueur);
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
                distribution();
                afficheMain();
                if (fold){
                    action = "flop";
                    deroulementPartie();
                }
                break;
            case "flop":
                flop();
                if (fold){
                    action = "turn";
                    deroulementPartie();
                }
                break;
            case "turn":
                turn();
                if (fold){
                    action = "river";
                    deroulementPartie();
                }
                break;
            case "river":
                river();
                if (fold){
                    action = "fin";
                    deroulementPartie();
                }
                break;
            case "fin":
                determineGagant();
                buttonPressed =0;
        }
    }

    /**
     * melange le deck de cartes
     */
    private void  melangeDeck(){
        indexCarte = 0; //on remet l'index a 0 à chaque melange de carte
        Collections.shuffle(deck);
    }

    private void distribution(){
        for (int i=0;i<2;i++){//distribution des cartes au joueur une par une
            for (Joueur joueur: listJoueur){
                Carte carte = deck.get(indexCarte);
                joueur.addCarteInMain(carte);
                indexCarte ++;
            }

        }

        for(int i = 0 ; i<5;i++){
            listCarteCentre.add(deck.get(indexCarte));
            indexCarte ++;
        }
    }

    private void afficheMain(){
        Carte carteMain1 = joueur.getMain().get(0);
        Carte carteMain2 = joueur.getMain().get(1);

        cardMain1   = (ImageView)findViewById(R.id.cardMain1);
        retourneCarte(carteMain1);
        cardMain2   = (ImageView)findViewById(R.id.cardMain2);
        retourneCarte(carteMain2);

    }

    /**
     * action du flop
     */
    private void flop (){
        Carte carteCentre1 = listCarteCentre.get(0);
        Carte carteCentre2 = listCarteCentre.get(1);
        Carte carteCentre3 = listCarteCentre.get(2);

        ImageView cardImage1 = (ImageView) findViewById(R.id.cardImage1);
        carteCentre1.setVueImage(cardImage1);
        retourneCarte(carteCentre1);
        ImageView cardImage2   = (ImageView)findViewById(R.id.cardImage2);
        carteCentre2.setVueImage(cardImage2);
        retourneCarte(carteCentre2);
        ImageView cardImage3   = (ImageView)findViewById(R.id.cardImage3);
        carteCentre3.setVueImage(cardImage3);
        retourneCarte(carteCentre3);


    }

    /**
     * action du turn
     */
    private void turn (){
        Carte carteCentre4 = listCarteCentre.get(3);
        ImageView cardImage4   = (ImageView)findViewById(R.id.cardImage4);
        carteCentre4.setVueImage(cardImage4);
        retourneCarte(carteCentre4);
    }

    /**
     * action de la river
     */
    private void river (){
        Carte carteCentre5 = listCarteCentre.get(4);
        ImageView cardImage5   = (ImageView)findViewById(R.id.cardImage5);
        retourneCarte(carteCentre5);
    }

    /**
     * retourne la prochaine carte du deck
     * @param carte
     */
    private void retourneCarte(Carte carte){
        Log.d("debug", carte.toString());
        ImageView vueCarte = carte.getVueImage();
        int identifier = getResources().getIdentifier(carte.getImgName(), "drawable", getPackageName());//transforme le string en drawable

        vueCarte.setImageResource(identifier);
        vueCarte.getLayoutParams().height=476;
        vueCarte.getLayoutParams().width=314;

    }

    public void afficheDos(Carte carte){
        carte.getVueImage().setImageResource(R.drawable.dos);
    }
    
    public void clique_bet(View v){
        buttonPressed++;
        Outils.makeToast(this, buttonPressed + "bet");
        buttonPresed();
    }


    
    public void clique_fold(View v){
        buttonPressed++;

        joueur.getMain().get(0);

        afficheDos(joueur.getMain().get(0));
        afficheDos(joueur.getMain().get(1));
        fold = true;


        buttonPresed();
    }

    private void buttonPresed(){
        if (buttonPressed == 1){
            action = "flop";

        }else if (buttonPressed == 2){
            action = "turn";

        }else if (buttonPressed == 3){
            action="river";

        }else if (buttonPressed == 4){
            action="fin";

        }
        deroulementPartie();
    }

    private void determineGagant() {
        String msg="";
        String titre="";
        fold = false;


        titre = "Bravo";
        msg = "Vous avez gagné !";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (Carte carteCentre: listCarteCentre){
                    afficheDos(carteCentre);
                }
                action = "initial";
                deroulementPartie();
            }
        });
        // Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_web);
        alertDialog.show();



    }

}
