package com.example.dbn.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.exceptions.CouleureException;
import com.example.alemariey.holdempoker.model.Carte;
import com.example.alemariey.holdempoker.model.Deck;
import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.outils.Outils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.alemariey.holdempoker.outils.Outils.chargePolices;


public class JeuxActivity extends AppCompatActivity {
    private String TAG = "JEUX";
    private String action = "initial";
    private Joueur joueurH;
    private List<Carte>deck = new ArrayList<>(Deck.getDeck());
    private List<Carte> listCarteCentre = new ArrayList<>();
    private List<Joueur> listJoueur = new ArrayList<>();
    
    private int indexCarte;
    private int buttonPressed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activité jeux");
        indexCarte = 0;

        setContentView(R.layout.activity_jeux);

        getJoueurs();//recupere l'objet joueurH de l'intent

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
        joueurH = (Joueur)intent.getSerializableExtra("joueur");
        //TODO ajouter recup nb d'ia et creer joueurs

        Joueur ia = new Joueur("Albert");

        listJoueur.add(joueurH);
        listJoueur.add(ia);
        afficheNomJoueur();


    }

    private void afficheNomJoueur(){
        TextView nomJoueur = (TextView)findViewById(R.id.txtNomJoueur);
        nomJoueur.setText(joueurH.getPseudo());

        stylise();
    }

    private void deroulementPartie(){
//        Log.d(TAG, "btnpresse" + buttonPressed);
        switch (action){
            case "initial":
                melangeDeck();
                distribution();
                for (Joueur joueur: listJoueur){
                    afficheMain(joueur); //TODO transformer affiche main enset main et refair affichemain
                }


                break;
            case "flop":
                flop();
                if (joueurH.isFold()){
                    action = "turn";
                    deroulementPartie();
                }
                break;
            case "turn":
                turn();
                if (joueurH.isFold()){
                    action = "river";
                    deroulementPartie();
                }
                break;
            case "river":
                river();
                if (joueurH.isFold()){
                    action = "fin";
                    deroulementPartie();
                }
                //TODO ajouter un case ou tout le monde montre ses cartes
                break;
            case "fin":
                determineGagant();

        }
    }
    private void initial(){
        listCarteCentre = new ArrayList<>();

        indexCarte = 0;
        buttonPressed = 0;



        for (Joueur joueur:listJoueur){
            joueur.reset();//remet les parametre du joueur a zero
        }


        action = "initial";
        deroulementPartie();
    }


    /**
     * melange le deck de cartes
     */
    private void  melangeDeck(){
        Collections.shuffle(deck);
    }

    private void distribution(){
        for (int i=0;i<2;i++){//distribution des cartes au joueurs une par une
            for (Joueur joueur: listJoueur){
                Carte carte = deck.get(indexCarte);
                joueur.addCarteInMain(carte);
                indexCarte ++;
            }

        }
//        debugTestMain(); //// TODO enlever pour la fin du debug
        for(int i = 0 ; i<5;i++){//distribution des cartes au centre
            listCarteCentre.add(deck.get(indexCarte));
            indexCarte ++;
        }
//        debugTestCentre();//TODO a comment
    }

    private void afficheMain(Joueur joueur){

        Carte carteMain1 = joueur.getMain().get(0);
        Carte carteMain2 = joueur.getMain().get(1);

        ImageView cardMain1   = (ImageView)findViewById(R.id.cardMain1);
        carteMain1.setVueImage(cardMain1);
        retourneCarte(carteMain1);
        ImageView cardMain2   = (ImageView)findViewById(R.id.cardMain2);
        carteMain2.setVueImage(cardMain2);
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
        carteCentre5.setVueImage(cardImage5);
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
//        vueCarte.getLayoutParams().height=476;
//        vueCarte.getLayoutParams().width=314;

    }

    public void afficheDos(Carte carte){
        carte.getVueImage().setImageResource(R.drawable.dos);
    }
    
    public void clique_bet(View v){
        buttonPressed++;
        Outils.makeToast(this, buttonPressed + "bet");
        buttonPressed();
    }


    
    public void clique_fold(View v){
        buttonPressed++;

        afficheDos(joueurH.getMain().get(0));
        afficheDos(joueurH.getMain().get(1));

        joueurH.fold();

        buttonPressed();
    }

    private void buttonPressed(){
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

        for (Joueur joueur : listJoueur){
            comptePoints(joueur);
        }

        String titre = "Bravo";
        String msg = "Vous avez gagné !";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(msg);

        for (Carte carteCentre : listCarteCentre) {//retourne les cartes du centre

            afficheDos(carteCentre);
        }
        initial();
        // Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_web);
        alertDialog.show();
    }


    private void comptePoints(Joueur joueur) {
        int pair = 0;
        int brelan = 0;
        boolean carre = false;
        int pasSuite = 0;
        boolean ace = false;
        boolean quinteFlush = false;

        int point = 0;
        String combinaison ="Carte haute";
        List<Carte> lCarteSuite = new ArrayList<>();

        List<Carte> lCarte = listCarteCentre;
        lCarte.add(joueur.getMain().get(0));
        lCarte.add(joueur.getMain().get(1));

        Collections.sort(lCarte, new Comparator<Carte>() {
            @Override
            public int compare(Carte c1, Carte c2) {
                return c1.compareTo(c2);
            }
        });



        for (int i = 0 ; i < lCarte.size();i++){ // determine si il y a : paire double paire brelan carré ou full
            Carte carte = lCarte.get(i);


            if (i<lCarte.size()-1){
                if ((carte.getPoid() == lCarte.get(i+1).getPoid())&&!carre){ //verif pair
                    point = 100;
                    combinaison = "Paire";

                    if (pair >0 && !carre && brelan == 0) {
                        combinaison = "Double Paire";

                        point = 200;
                    }

                    pair ++;
                    i++;
                    if (i<lCarte.size()-1){
                        if ((carte.getPoid() == lCarte.get(i+1).getPoid())&&!carre){// verif brelan
                            brelan ++;
                            pair --;
                            point = 200;
                            combinaison = "Brelan";
                            i++;
                            if (i<lCarte.size()-1) {
                                if (carte.getPoid() == lCarte.get(i + 1).getPoid()) {//verif carré
                                    point = 700;
                                    combinaison = "Carre";
                                    carre = true;
                                    brelan --;
                                    i++;
                                }
                            }
                        }
                    }
                    if (i > lCarte.size() ){
                        break;
                    }
                }
            }
        }
        if ((brelan>0 && pair>0) || (brelan >1)){
            combinaison = "Full";
            point = 600;
        }

        if (combinaison != "Full" &&  !carre && isCouleur(lCarte)){//on cherche les couleurs
            combinaison = "Couleur";
            point = 600;
        }


        for (int j = 0 ; j < lCarte.size();j++){// on cherche les suites
            Carte card = lCarte.get(j);
            if (card.getPoid() == 14){ //on regarde si c'est un as
                ace = true;
            }
            if (lCarteSuite.size() == 0){
                lCarteSuite.add(card);
            }else {
                if (card.getPoid() == lCarteSuite.get(lCarteSuite.size() - 1).getPoid() ){// si ce sont les meme carte on ne fait rien

                }
                else if(card.getPoid() + 1 == lCarteSuite.get(lCarteSuite.size() - 1).getPoid() ){ //si les deux carte se suivent
                    lCarteSuite.add(card);

                    if (((card.getPoid() == 2) && (lCarteSuite.size() == 4) && ace)) { //si la derniere carte et 2 et qu'il y a un as
                        if (isCouleur(lCarte)){

                            combinaison = "Quinte Flush";
                            point = 800 + lCarteSuite.get(0).getPoid();
                        }else {
                            combinaison = "suite";
                            point = 405; //plus petite suite
                        }


                    }
                }else {
                    lCarteSuite = new ArrayList<>();

                }
                if ((lCarteSuite.size() == 5)) {//si la liste fait 5 cartes

                    if (isCouleur(lCarte)){

                        combinaison = "Quinte Flush";
                        point = 800 + lCarteSuite.get(0).getPoid();//jusqu'a 814
                        if (point == 814){
                            combinaison = "Quinte Flush Royal";
                            point = 1000;
                        }
                    }else {
                        combinaison = "suite";
                        point = 400 + lCarteSuite.get(0).getPoid();//jusqu'a 414
                        break;
                    }
                }
            }
        }


        Log.d(TAG, combinaison);
        joueur.addPoint(point);
        joueur.setCombinaison(combinaison);
    }

    /**
     * range la liste de carte par couleur et regarde si il y en a 5 qui se suivent
     * @param lCarte la liste des cartes
     * @return un boolean pour savoir si c'est une couleur ou non
     */
    private boolean isCouleur(List<Carte> lCarte){
        List<Carte> lCouleur = new ArrayList<>();
        boolean couleur = false;

        Collections.sort(lCarte, new Comparator<Carte>() {
            @Override
            public int compare(Carte c1, Carte c2) {//range la liste par couleur
                return c1.getCouleur().compareToIgnoreCase(c2.getCouleur());
            }
        });
        for (Carte carte: lCarte){
            if (lCouleur.size()== 0 ){
                lCouleur.add(carte);
            }else {
                if (lCouleur.get(lCouleur.size()-1).getCouleur() == carte.getCouleur()){
                    lCouleur.add(carte);
                }else {
                    lCouleur = new ArrayList<>();
                }
            }
        }
        if (lCouleur.size() >= 5){
            couleur = true;
        }
        return couleur;
    }


    private void stylise(){

        TextView txtNomIa = (TextView) findViewById(R.id.txtNomIa);
        TextView txtNomJoueur = (TextView) findViewById(R.id.txtNomJoueur);

        Button btnBet = (Button) findViewById(R.id.buttonBet);
        Button btnCheck = (Button) findViewById(R.id.buttonCheck);
        Button btnFold = (Button) findViewById(R.id.buttonFold);

        chargePolices(this, txtNomIa);
        chargePolices(this, txtNomJoueur);

        chargePolices(this, btnBet);
        chargePolices(this, btnCheck);
        chargePolices(this, btnFold);

        txtNomIa.setTextColor(Color.WHITE);
        txtNomJoueur.setTextColor(Color.WHITE);

        int txtColorVert = ContextCompat.getColor(this, R.color.darkgreen);
        btnBet.setTextColor(txtColorVert);

        btnFold.setTextColor(Color.RED);

        //size
        btnBet.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        btnCheck.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        btnFold.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);

        txtNomIa.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        txtNomJoueur.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
    }

    private void debugTestMain(){
        try {
            Carte c1 = new Carte("PIQUE",13);
            Carte c2 = new Carte("PIQUE",14);

            joueurH.addCarteInMain(c1);
            joueurH.addCarteInMain(c2);
            
        } catch (CouleureException e) {

        }

    }

    private void debugTestCentre(){
        try {
            Carte c1 = new Carte("PIQUE",14);
            Carte c2 = new Carte("PIQUE",14);
            Carte c3 = new Carte("PIQUE",10);
            Carte c4 = new Carte("PIQUE",11);
            Carte c5 = new Carte("PIQUE",12);
            listCarteCentre = new ArrayList<>();
            listCarteCentre.add(c1);
            listCarteCentre.add(c2);
            listCarteCentre.add(c3);
            listCarteCentre.add(c4);
            listCarteCentre.add(c5);
        } catch (CouleureException e) {

        }

    }
}
