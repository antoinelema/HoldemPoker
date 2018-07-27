package com.example.dbn.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alemariey.holdempoker.HomeActivity;
import com.example.alemariey.holdempoker.MyApplication;
import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.exceptions.CouleureException;
import com.example.alemariey.holdempoker.model.Carte;
import com.example.alemariey.holdempoker.model.Deck;
import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.model.JoueurManager;
import com.example.alemariey.holdempoker.outils.Outils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.alemariey.holdempoker.outils.Outils.chargePolices;

/**
 * activité du jeu
 */
public class JeuxActivity extends AppCompatActivity {
    private boolean debug = false; //variable pour le debug, si sur true: modifier les fonction debugTestCentre() et debugTestMain() à la fin du fichier pour atribuer les cartes que vous desirez

    private final String TAG = "JEUX";
    private String action = "initial";// action de la partie
    private Joueur joueurH; //le joueur humain
    private List<Carte>deck = new ArrayList<>(Deck.getDeck()); // le deck de carte
    private List<Carte> listCarteCentre = new ArrayList<>(); // la liste des cartes au centre
    private List<Joueur> listJoueur = new ArrayList<>(); //la liste des joueurs
    
    private int pot; //le pot a gagner
    private int indexCarte; //l'index de la prochaine carte du deck a retourner
    private int buttonPressed = 0; // nombre de fois ou le bouton bet ou check à été pressé
    private View btnFin; //vus du bouton fin
    private Button btnBet; //boutton bet
    private Button btnCheck; // bouton Check
    private Button btnFold; // bouton fold

    private  boolean fin = true; //si on peu finir ou pas
    private boolean gotToFin;// permet de passer directement a la fin si true


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.i(TAG, "Activité jeux");
        getSupportActionBar().hide();
        indexCarte = 0;

        setContentView(R.layout.activity_jeux);
        btnFin = findViewById(R.id.buttonFin);
        btnBet = (Button) findViewById(R.id.buttonBet);
        btnCheck = (Button) findViewById(R.id.buttonCheck);
        btnFold = (Button) findViewById(R.id.buttonFold);
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


    @Override
    public void onBackPressed() {
        fin();
    }

//------------------------------------ Fonctions applicative ---------------------------------------

    /**
     * recupere le joueur et l'ia dans l'intent
     */
    private void getJoueurs() {
        Intent intent = getIntent();
        joueurH = (Joueur)intent.getSerializableExtra("joueur");

        Joueur ia = new Joueur("Albert",500);

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
        TextView nbJetonJH = (TextView)findViewById(R.id.txtNbJetonsH);
        TextView nbJetonIA = (TextView)findViewById(R.id.txtJetonsIa);

        nomJoueur.setText(listJoueur.get(0).getPseudo());
        nbJetonJH.setText(listJoueur.get(0).getNbJeton()+"$");
        nomIa.setText(listJoueur.get(1).getPseudo());
        nbJetonIA.setText(listJoueur.get(1).getNbJeton() + "$");

        nbJetonIA.setTextColor(ContextCompat.getColor(this, R.color.lightblue));
        nbJetonJH.setTextColor(ContextCompat.getColor(this, R.color.lightblue));
        Outils.chargePolices(this, nbJetonJH, MyApplication.getANDERSON());
        Outils.chargePolices(this, nbJetonIA, MyApplication.getANDERSON());

        stylise();
    }

    /**
     * deroulement de la partie en fonction de la variable action
     */
    private void deroulementPartie(){
        switch (action){
            case "initial":
                melangeDeck();
                distribution();

                afficheMainJ(joueurH);

                break;
            case "flop":
                flop();
                verifZero();
                btnFin.setVisibility(View.INVISIBLE);
                fin = false;
                if (gotToFin){
                    action = "turn";
                    deroulementPartie();
                }
                break;
            case "turn":
                turn();
                verifZero();
                if (gotToFin){
                    action = "river";
                    deroulementPartie();

                }
                break;
            case "river":
                river();
                verifZero();
                if (gotToFin){
                    action = "fin";
                    deroulementPartie();
                }
                break;
            case "fin":
                btnBet.setClickable(false);//bouton non cliquable pour eviter les double clique
                btnCheck.setClickable(false);//qui font planter l'apli à la fin
                btnFold.setClickable(false);
                afficheMainIA();
                determineGagant();
        }
    }

    /**
     * initialise ou reinitialise les variables et les objets
     */
    private void initial(){
        TextView txtPot = (TextView)findViewById(R.id.txtPot);
        TextView txtJetonIa = (TextView)findViewById(R.id.txtJetonsIa);
        TextView txtJetonH = (TextView)findViewById(R.id.txtNbJetonsH);

        btnBet.setClickable(true);
        btnCheck.setClickable(true);
        btnFold.setClickable(true);
        btnFin.setVisibility(View.VISIBLE);
        gotToFin = false;
        listCarteCentre = new ArrayList<>();
        pot = 0;
        txtPot.setText(pot+"$");
        indexCarte = 0;
        buttonPressed = 0;

        for (Joueur joueur:listJoueur){
            afficheDos(joueur.getMain().get(0));
            afficheDos(joueur.getMain().get(1));
            joueur.reset();//remet les parametre du joueur a zero
        }

        txtJetonH.setText(joueurH.getNbJeton()+"$");
        txtJetonIa.setText(listJoueur.get(1).getNbJeton()+"$");

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
        if (!debug){
            for (int i=0;i<2;i++){//distribution des cartes au joueurs une par une
                for (Joueur joueur: listJoueur){
                    Carte carte = deck.get(indexCarte);
                    joueur.addCarteInMain(carte);
                    indexCarte ++;
                }
            }

            for(int i = 0 ; i<5;i++){//distribution des cartes au centre
                listCarteCentre.add(deck.get(indexCarte));

                indexCarte ++;
            }
        }else {
            debugTestMain(listJoueur.get(1));
            debugTestCentre();
        }


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
     * parie 10$ et passe au tour suivant
     * @param v
     */
    public void clique_bet(View v){
        TextView txtPot = (TextView)findViewById(R.id.txtPot);
        TextView txtJetonIa = (TextView)findViewById(R.id.txtJetonsIa);
        TextView txtJetonH = (TextView)findViewById(R.id.txtNbJetonsH);

        Outils.makeToast(this,"Pari de 10$");
        joueurH.subJeton(10);
        listJoueur.get(1).subJeton(10);//ia
        pot +=20;

        txtPot.setText(pot + "$");
        txtJetonH.setText(joueurH.getNbJeton()+"$");
        txtJetonIa.setText(listJoueur.get(1).getNbJeton()+"$");

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
        gotToFin = true;
        buttonPressed();
    }

    /**
     * lance l'action corepondant au nombre de bouton ( ou check) pressé
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
     * determine le gagnant en fonction du nombre de points
     */
    private void determineGagant() {
        fin = true; //on peu quitter
        String titre = "Fin du tour";
        String msg = "";
        List<Joueur> winnerOrder = new ArrayList<>();

        for (Joueur joueur : listJoueur){
            if (!joueur.isFold()){ //si le joueur n'est pas fold
                comptePoints(joueur);
                winnerOrder.add(joueur);
            }
        }

        Collections.sort(winnerOrder, new Comparator<Joueur>() { //range les joueur dans l'odre de leur points
            @Override
            public int compare(Joueur j1, Joueur j2) {
                return j1.compareTo(j2);
            }
        });
        int nbEquals = isEquality(winnerOrder);//nombre d' egaliter

        if (nbEquals>=1){ //les joueur a egaliter remporte le pot diviser par le nombre de joueur a egalité
            msg = "Les joueurs ";
            for (int i = 0 ; i <= nbEquals; i++){
                msg += winnerOrder.get(i).getPseudo() + ", ";
            }
            msg += "sont arrivés à egalité et se partagent le pot.";
            pot = pot/2;
            winnerOrder.get(0).addJeton(pot);
            winnerOrder.get(1).addJeton(pot);
            //TODO modulo de pot pour les egalité avec un nombre impaire

        }else { // le 1er joueur remporte le pot
            winnerOrder.get(0).addJeton(pot);
            msg = winnerOrder.get(0).getPseudo()+" gagne avec "+winnerOrder.get(0).getCombinaison();
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(msg);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (joueurH.getNbJeton() == 0 || (listJoueur.get(1).getNbJeton()==0)) {
                    msgfin();//quand un joueur arrive a zero jeton l'autre a gagné , affiche le message de fin et retourne a la page home
                }else {
                    for (Carte carteCentre : listCarteCentre) {//retourne les cartes du centre
                        afficheDos(carteCentre);
                    }
                    initial();//on reinitialise le jeux
                }

            }
        });


        // Set the Icon for the Dialog
//        alertDialog.setIcon(R.drawable.ic_launcher_web); taille trop grande sur la petite tablette
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

                if (winnerOrder.get(i).getPoint() == winnerOrder.get(i+1).getPoint()){ //si c'est toujour egale après la main haute
                    nbEquality ++;
                }
            }

        }

        return nbEquality;
    }

    /**
     * message de fin
     */
    private  void msgfin(){
        String msgFin = "";

        if (joueurH.getNbJeton() == 0) {
            msgFin = listJoueur.get(1).getPseudo();

        } else if (listJoueur.get(1).getNbJeton() == 0) {
            msgFin = joueurH.getPseudo();
        }

        AlertDialog alertDialogFin = new AlertDialog.Builder(this).create();
        alertDialogFin.setTitle(msgFin + " gagne la partie");
        alertDialogFin.setMessage(msgFin + " gagne la partie !");
        alertDialogFin.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fin(); //retourne à la page home
            }
        });

        // Set the Icon for the Dialog
        alertDialogFin.setIcon(R.drawable.ic_launcher_web);
        alertDialogFin.setCancelable(false);

        alertDialogFin.show();
    }

    /**
     * compte les point du joueur entré en parametre en fonction de ses cartes
     * @param joueur
     */
    private void comptePoints(Joueur joueur) {
        int pair = 0;
        int brelan = 0;

        boolean ace = false; //si il y a un as
        boolean carre = false;
        boolean full = false;


        int point = 10;//intit a 10 pour eviter de fair des bug d'egalités
        String combinaison ="une Carte haute";
        List<Carte> lCarteSuite = new ArrayList<>();

        List<Carte> lCarte = new ArrayList(listCarteCentre);
        lCarte.add(joueur.getMain().get(0));
        lCarte.add(joueur.getMain().get(1));

        Collections.sort(lCarte, new Comparator<Carte>() {//rengement des cartes par poid
            @Override
            public int compare(Carte c1, Carte c2) {
                return c1.compareTo(c2);
            }
        });


        for (int i = 0 ; i < lCarte.size();i++){ // determine si il y a : paire double paire brelan carré ou full
            Carte carte = lCarte.get(i);

            if (i<lCarte.size()-1){
                if ((carte.getPoid() == lCarte.get(i+1).getPoid())&&!carre){ //verif pair
                    point = 100+carte.getPoid();
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

        mainHaute(listJoueur.get(0), listJoueur.get(1));

        joueur.addPoint(point);
        joueur.setCombinaison(combinaison);
    }

    /**
     * verification de la carte la plus haute entre les carte de 2 joueurs
     * @param joueur1
     * @param joueur2
     */
    private void mainHaute(Joueur joueur1, Joueur joueur2){
        int carte1Joueur1 = joueur1.getMain().get(0).getPoid();
        int carte2Joueur1 = joueur1.getMain().get(1).getPoid();
        int carte1Joueur2 = joueur2.getMain().get(0).getPoid();
        int carte2Joueur2 = joueur2.getMain().get(1).getPoid();

        if ((carte1Joueur1 == carte1Joueur2) || (carte1Joueur1 == carte2Joueur2)||(carte2Joueur1 == carte1Joueur2) || (carte2Joueur1 == carte2Joueur2)) { //si la 1ere carte su joueur 1 egale a une carte du joueur2
            joueur1.addPoint(carte1Joueur1 + carte2Joueur1);
            joueur2.addPoint(carte1Joueur2 + carte2Joueur2);
        }
        else if ((carte1Joueur1 > carte1Joueur2 && carte1Joueur1 > carte2Joueur2)||(carte2Joueur1>carte1Joueur2 && carte2Joueur1 > carte2Joueur2)){
            joueur1.addPoint(15);
        }else{
            joueur2.addPoint(15);
        }

    }

    /**
     * range la liste de carte par couleur et regarde si il y en a 5 qui se suivent
     * @param lCarte la liste des cartes
     * @return un boolean pour savoir si c'est une couleur ou non
     */
    private boolean isCouleur(List<Carte> lCarte){
        List<Carte> lCouleur = new ArrayList<>(lCarte);
        boolean couleur = false;

        Collections.sort(lCouleur, new Comparator<Carte>() {
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
     * verification si un joueur arrive à zero jeton
     */
    public void verifZero(){
        if (!gotToFin){
            for (Joueur joueur : listJoueur){
                if (joueur.getNbJeton() == 0){
                    gotToFin = true;
                }
            }
        }

    }

    /**
     * stylise le plateau de jeux
     */
    private void stylise(){

        TextView txtNomIa = (TextView) findViewById(R.id.txtNomIa);
        TextView txtNomJoueur = (TextView) findViewById(R.id.txtNomJoueur);
        TextView lblPot = (TextView)findViewById(R.id.lblPot);
        TextView txtPot = (TextView)findViewById(R.id.txtPot);


        Button btnFin = (Button) findViewById(R.id.buttonFin);

        txtPot.setText(pot+"$");

        chargePolices(this, txtNomIa, MyApplication.getRIOGRANDE());
        chargePolices(this, txtNomJoueur, MyApplication.getRIOGRANDE());
        chargePolices(this,btnFin,MyApplication.getRIOGRANDE());

        chargePolices(this, btnBet,MyApplication.getRIOGRANDE());
        chargePolices(this, btnCheck,MyApplication.getRIOGRANDE());
        chargePolices(this, btnFold,MyApplication.getRIOGRANDE());
        chargePolices(this, lblPot,MyApplication.getRIOGRANDE());
        chargePolices(this, txtPot, MyApplication.getANDERSON());

        txtNomIa.setTextColor(Color.WHITE);
        txtNomJoueur.setTextColor(Color.WHITE);
        txtPot.setTextColor(ContextCompat.getColor(this, R.color.lightblue));
        lblPot.setTextColor(ContextCompat.getColor(this, R.color.red));

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

    public void clique_fin(View v){
        fin();
    }

    /**
     * update le joueur dans la bdd, start l'activité home et ferme l'activité jeux
     * 
     */
    private void fin(){
        if (fin){
            JoueurManager.upDateJoueur(joueurH);
            Intent intent = new Intent(this, HomeActivity.class);// New activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            Outils.makeToast(this,"veuillez finir le tour avant de quitter");
        }

    }

// -------------------------------------- DEBUG ----------------------------------------------------
//
    /**
     * donne des cartes predefinie au joueur
     * @param joueur
     */
    private void debugTestMain(Joueur joueur){
        try {
            Carte c1 = new Carte(Deck.TREFLE,9);
            Carte c2 = new Carte(Deck.CARREAU,8);

            joueurH.addCarteInMain(c1);
            joueurH.addCarteInMain(c2);

            Carte c3 = new Carte(Deck.CARREAU,9);
            Carte c4 = new Carte(Deck.PIQUE,12);

            joueur.addCarteInMain(c3);
            joueur.addCarteInMain(c4);

        } catch (CouleureException e) {

        }

    }

    /**
     * donne des cartes predefinie au centre
     */
    private void debugTestCentre(){
        try {
            Carte c1 = new Carte(Deck.TREFLE,9);
            Carte c2 = new Carte(Deck.TREFLE,14);
            Carte c3 = new Carte(Deck.TREFLE,14);
            Carte c4 = new Carte(Deck.CARREAU,7);
            Carte c5 = new Carte(Deck.COEUR,4);
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
