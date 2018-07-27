package com.example.alemariey.holdempoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.model.JoueurManager;
import com.example.alemariey.holdempoker.outils.Outils;
import com.example.dbn.myapplication.AddActivity;
import com.example.dbn.myapplication.AddJetonsActivity;
import com.example.dbn.myapplication.JeuxActivity;

import java.util.ArrayList;

import static com.example.alemariey.holdempoker.outils.Outils.chargePolices;
import static com.example.alemariey.holdempoker.outils.Outils.makeToast;

public class HomeActivity extends Activity {

    private String TAG = "HOME";
    private ArrayList<Joueur> lJoueurs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getActionBar().hide();

        Spinner spinnerJoueur = (Spinner) this.findViewById(R.id.spinnerJoueur);
        chargeJoueurs();
        chargeSpinner(spinnerJoueur);

        stylise(); // Ajout des polices
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
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
        Outils.makeToast(this,"au revoir");
        finish();
    }

    /**
     * ajout des polices sur les labels et boutons
     */
    private void stylise(){

        TextView lblChoixJoueur = (TextView) findViewById(R.id.lblChoixJoueur);
        Button btnjouer = (Button) findViewById(R.id.btnJouer);
        Button btnAdd = (Button) findViewById(R.id.buttonAddJoueur);
        Button btnAddJetons = (Button) findViewById(R.id.buttonAddJetons);


        chargePolices(this, lblChoixJoueur, MyApplication.getRIOGRANDE());
        chargePolices(this, btnjouer,MyApplication.getRIOGRANDE());
        chargePolices(this, btnAdd,MyApplication.getRIOGRANDE());
        chargePolices(this, btnAddJetons,MyApplication.getRIOGRANDE());
    }


    /**
     * charge le spiner avec le nom des joueurs et ajoute une typo
     * @param spinner
     */
    public void chargeSpinner(Spinner spinner) {

        ArrayList lJoueursSpinner = new ArrayList(lJoueurs);


        ArrayAdapter<Joueur> itemsAdapter = new ArrayAdapter<Joueur>(this, R.layout.spinner_items, lJoueursSpinner) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

               Outils.chargePolices(MyApplication.getAppContext(), (TextView) v, MyApplication.getANDERSON());

                return v;
            }


            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v =super.getDropDownView(position, convertView, parent);

                Outils.chargePolices(MyApplication.getAppContext(), (TextView) v, MyApplication.getANDERSON());

                return v;
            }
        };

        spinner.setAdapter(itemsAdapter);
    }

    /**
     * select sur les joueur dans la bdd
     */
    private void chargeJoueurs() {
        this.lJoueurs = (ArrayList)JoueurManager.getAll();
    }



    /**
     * lance la partie si le choix du joueur n'est pas vide
     * @param v
     */
    public void clique_jouer(View v){
        Log.i(TAG, "clique_jouer ");

        String text;
        Spinner spinnerJoueur = (Spinner)findViewById(R.id.spinnerJoueur);
        Joueur joueur =(Joueur)spinnerJoueur.getSelectedItem();

        if (joueur.toString().isEmpty()){
            text = getString(R.string.errSpinerJoueur);
            makeToast(this,text);
        }else {
            //on commence à jouer
            //intent
            Intent intent = new Intent(HomeActivity.this, JeuxActivity.class);
            // On affecte à l'Intent les données à passer
            intent.putExtra("joueur",joueur);

            startActivity(intent);
        }
    }

    public void clique_addJoueur(View v){
        Log.i(TAG, "clique_addJoueur ");

        Intent intent = new Intent(HomeActivity.this, AddActivity.class);

        startActivity(intent);
    }

    /**
     * onclick du boutton addjetons, envoie sur l'activité pour ajouter des jetons
     * @param v
     */
    public void clique_addJetons(View v){
        Log.i(TAG, "clique_addJetons ");
        Spinner spinnerJoueur = (Spinner)findViewById(R.id.spinnerJoueur);
        Joueur joueur =(Joueur)spinnerJoueur.getSelectedItem();

        Intent intent = new Intent(HomeActivity.this, AddJetonsActivity.class);
        intent.putExtra("joueur",joueur);
        startActivity(intent);
    }

}
