package com.example.dbn.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        getSupportActionBar().hide();
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

    /**
     * recupere le joueur et l'ia des l'intent
     */
    private void getJoueurs() {
        Intent intent = getIntent();
        joueurH = (Joueur)intent.getSerializableExtra("joueur");

        Joueur ia = new Joueur("Albert");

        listJoueur.add(joueurH);
        listJoueur.add(ia);
        afficheNomJoueur();
    }

    /**
     * affiche le nom du joueur et de l'ia a coté de leur jeux respectif
     */
    private void afficheNomJoueur(){
        TextView nomJoueur = (TextView)findViewById(R.id.txtNomJoueur);
        TextView nomIa = (TextView)findViewById(R.id.txtNomIa);

        nomJoueur.setText(listJoueur.get(0).getPseudo());
        nomIa.setText(listJoueur.get(1).getPseudo());

        stylise();
    }

    /**
     * deroulement de la partie en fonction de la variable action
     */
    private void deroulementPartie(){
//        Log.d(TAG, "btnpresse" + buttonPressed);
        switch (action){
            case "initial":
                melangeDeck();
                distribution();

                afficheMainJ(joueurH);


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
                break;
            case "fin":
                afficheMainIA();
                determineGagant();
        }
    }

    /**
     * initialise ou reinitialise les variables et les objets
     */
    private void initial(){
        listCarteCentre = new ArrayList<>();

        indexCarte = 0;
        buttonPressed = 0;

        for (Joueur joueur:listJoueur){
            afficheDos(joueur.getMain().get(0));
            afficheDos(joueur.getMain().get(1));
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

    /**
     * distribue les cartes : 2 a chaque joueur puis 5 au centre
     */
    private void distribution(){
        for (int i=0;i<2;i++){//distribution des cartes au joueurs une par une
            for (Joueur joueur: listJoueur){
                Carte carte = deck.get(indexCarte);
                joueur.addCarteInMain(carte);
                indexCarte ++;
            }
        }
//        debugTestMain(listJoueur.get(1)); //// TODO enlever pour la fin du debug
        for(int i = 0 ; i<5;i++){//distribution des cartes au centre
            listCarteCentre.add(deck.get(indexCarte));

            indexCarte ++;
        }
//        debugTestCentre();//TODO a comment
    }

    /**
     * affiche la main du joueur
     * @param joueur
     */
    private void afficheMainJ(Joueur joueur){

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
     * affiche la main de l'ia
     */
    private void afficheMainIA(){
        Joueur joueur = listJoueur.get(1);

        Carte carteMain1 = joueur.getMain().get(0);
        Carte carteMain2 = joueur.getMain().get(1);

        ImageView cardMain1   = (ImageView)findViewById(R.id.carteIA1);
        carteMain1.setVueImage(cardMain1);
        retourneCarte(carteMain1);
        ImageView cardMain2   = (ImageView)findViewById(R.id.carteIA2);
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
    private void turn() {
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
     * affiche la face d'une carte
     * @param carte
     */
    private void retourneCarte(Carte carte){

        Log.d("debug", carte.toString());


        ImageView vueCarte = carte.getVueImage();
        int identifier = getResources().getIdentifier(carte.getImgName(), "drawable", getPackageName());//transforme le string en drawable

        vueCarte.setImageResource(identifier);

    }

    /**
     * affiche le dos de la carte passé en parametre
     * @param carte
     */
    public void afficheDos(Carte carte){
        carte.getVueImage().setImageResource(R.drawable.dos);
    }

    /**
     * passe au tour suivant
     * @param v
     */
    public void clique_bet(View v){
        clique_check(v);
    }

    /**
     * passe au tour suivant
     * @param v
     */
    public void clique_check(View v){
        buttonPressed++;
        buttonPressed();
    }


    /**
     * le joueur se couche pour le tour
     * @param v
     */
    public void clique_fold(View v){
        buttonPressed++;

        afficheDos(joueurH.getMain().get(0));
        afficheDos(joueurH.getMain().get(1));

        joueurH.fold();

        buttonPressed();
    }

    /**
     * lance l'action corepondant au nombre de bouton (bet ou check) pressé
     */
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

    /**
     * determine le gagnant en fonction du nombre de ses points
     */
    private void determineGagant() {

        String titre = "Fin du tour";
        String msg = "";

        for (Joueur joueur : listJoueur){
            if (!joueur.isFold()){ //si le joueur n'est pas fold
                comptePoints(joueur);
            }

        }
        List<Joueur> winnerOrder = new ArrayList<>(listJoueur);
        Log.d(TAG, winnerOrder.toString());

        Collections.sort(winnerOrder, new Comparator<Joueur>() {
            @Override
            public int compare(Joueur j1, Joueur j2) {
                return j1.compareTo(j2);
            }
        });

        int nbEquals = isEquality(winnerOrder);

        if (nbEquals>=1){
            msg = "Les joueurs ";
            for (int i = 0 ; i <= nbEquals; i++){
                msg += winnerOrder.get(i).getPseudo() + ", ";
            }
            msg += "sont arrivés à egalité et se partagent le pot.";

        }else {
            msg = winnerOrder.get(0).getPseudo()+" gagne avec "+winnerOrder.get(0).getCombinaison();
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(msg);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Carte carteCentre : listCarteCentre) {//retourne les cartes du centre
                    afficheDos(carteCentre);
                }
                initial();
            }
        });


        // Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.ic_launcher_web);

        alertDialog.setCancelable(false);

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.RIGHT|Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        alertDialog.show();
        
    }

    /**
     * recherche le nombre de joueur a egalité
     * @param winnerOrder liste de joueur dans l'odre croissant de points
     * @return nombre de joueur a egalité
     */
    private int isEquality(List<Joueur> winnerOrder) {
        int nbEquality = 0 ;

        for (int i = 0; i < winnerOrder.size();i++){


            if (i< (winnerOrder.size()-1)){
                if (winnerOrder.get(i).getPoint() == winnerOrder.get(i+1).getPoint()){

                    nbEquality ++;
                }
            }

        }
        return nbEquality;
    }

    /**
     * compte les point du joueur entré en parametre en fonction de ses cartes
     * @param joueur
     */
    private void comptePoints(Joueur joueur) {
        int pair = 0;
        int brelan = 0;
        int pasSuite = 0;
        boolean ace = false; //si il y a un as
        boolean carre = false;
        boolean full = false;
        boolean quinteFlush = false;

        int point = 0;
        String combinaison ="une Carte haute";
        List<Carte> lCarteSuite = new ArrayList<>();

        List<Carte> lCarte = new ArrayList(listCarteCentre);
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
                    combinaison = "une Paire";

                    if (pair >0 && !carre && brelan == 0) {
                        combinaison = "une Double Paire";

                        point = 200;
                    }

                    pair ++;
                    i++;
                    if (i<lCarte.size()-1){
                        if ((carte.getPoid() == lCarte.get(i+1).getPoid())&&!carre){// verif brelan
                            brelan ++;
                            pair --;
                            point = 300;
                            combinaison = "un Brelan";
                            i++;
                            if (i<lCarte.size()-1) {
                                if (carte.getPoid() == lCarte.get(i + 1).getPoid()) {//verif carré
                                    point = 800;
                                    combinaison = "un Carre";
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
            combinaison = "un Full";
            full = true;
            point = 700;
        }

        if (!full &&  !carre && isCouleur(lCarte)){//on cherche les couleurs
            combinaison = "une Couleur";
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

                            combinaison = "une Quinte Flush";
                            point = 900 + lCarteSuite.get(0).getPoid();
                        }else {
                            combinaison = "une suite";
                            point = 405; //plus petite suite
                        }


                    }
                }else {
                    lCarteSuite = new ArrayList<>();

                }
                if ((lCarteSuite.size() == 5)) {//si la liste fait 5 cartes

                    if (isCouleur(lCarte)){

                        combinaison = "une Quinte Flush";
                        point = 900 + lCarteSuite.get(0).getPoid();//jusqu'a 914
                        if (point == 914){
                            combinaison = "une Quinte Flush Royal";
                            point = 1000;
                        }
                    }else {
                        combinaison = "une suite";
                        point = 400 + lCarteSuite.get(0).getPoid();//jusqu'a 414
                        break;
                    }
                }
            }
        }

        if ((combinaison == "une Carte haute")||(brelan>0)||(pair>0)){ //l'addition des 5 plus haute cartes pour gerer les cas d'egalités

            for (int i=0; i<5 ; i++){
                point += lCarte.get(i).getPoid();
            }
        }


        Log.d(TAG, joueur.getPseudo() + " : " + combinaison);
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

    /**
     * stylise le plateau de jeux
     */
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

    private void debugTestMain(Joueur joueur){
        try {
            Carte c1 = new Carte("PIQUE",14);
            Carte c2 = new Carte("PIQUE",3);

            joueurH.addCarteInMain(c1);
            joueurH.addCarteInMain(c2);

            Carte c3 = new Carte("PIQUE",12);
            Carte c4 = new Carte("PIQUE",14);

            joueur.addCarteInMain(c3);
            joueur.addCarteInMain(c4);

        } catch (CouleureException e) {

        }

    }

    private void debugTestCentre(){
        try {
            Carte c1 = new Carte("PIQUE",13);
            Carte c2 = new Carte("COEUR",14);
            Carte c3 = new Carte("CARREAU",10);
            Carte c4 = new Carte("TREFLE",9);
            Carte c5 = new Carte("PIQUE",4);
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
