package com.example.dbn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.exceptions.CouleureException;
import com.example.alemariey.holdempoker.model.Carte;
import com.example.alemariey.holdempoker.model.Deck;
import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.outils.Outils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class JeuxActivity extends AppCompatActivity {
    private String TAG = "JEUX";
    private Joueur joueur;
    private List<Carte>deck = new ArrayList<>(Deck.getDeck());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activité jeux");
        setContentView(R.layout.activity_jeux);

        getJoueur();//recupere l'objet joueur de l'intent

        TextView txt =(TextView) findViewById (R.id.txtTest);
        txt.setText(joueur.getPseudo());

//        Log.d(TAG, deck.get(1).getImg());

        for (Carte carte : deck) {
            Log.d(TAG, carte.toString());
        }

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

    private void getJoueur() {
        Intent intent = getIntent();
        joueur = (Joueur)intent.getSerializableExtra("joueur");
    }



}
