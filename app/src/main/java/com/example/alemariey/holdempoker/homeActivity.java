package com.example.alemariey.holdempoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.model.JoueurManager;
import com.example.dbn.myapplication.JeuxActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static com.example.alemariey.holdempoker.outils.Outils.chargePolices;
import static com.example.alemariey.holdempoker.outils.Outils.makeToast;

public class HomeActivity extends Activity {

    private String TAG = "HOME";
    private ArrayList<Joueur> joueurs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Spinner spinnerJoueur = (Spinner) this.findViewById(R.id.spinnerJoueur);
        chargeJoueurs();
        chargeSpinner(spinnerJoueur);

        stylise(); // Ajout des polices
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    /**
     * ajout des polices sur les labels et boutons
     */
    private void stylise(){

//        TextView spinnerItems = (TextView)findViewById( R.id.textSpinner);
        TextView lblChoixJoueur = (TextView) findViewById(R.id.lblChoixJoueur);
        Button btnjouer = (Button) findViewById(R.id.btnJouer);

//        chargePolices(this, spinnerItems);
        chargePolices(this, lblChoixJoueur);
        chargePolices(this, btnjouer);


    }



    public void chargeSpinner(Spinner spinner){

        ArrayAdapter<Joueur> adapter = new ArrayAdapter<>(this,R.layout.spinner_items, this.joueurs);
        spinner.setAdapter(adapter);
    }

    private void chargeJoueurs() {
        Map<String, Joueur> conversionTable = JoueurManager.getTblJoueur();
        this.joueurs = new ArrayList<>(conversionTable.values());

        // Tri des devises
//        Collections.sort(this.joueurs);
    }

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
}
