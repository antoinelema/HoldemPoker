package com.example.dbn.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alemariey.holdempoker.HomeActivity;
import com.example.alemariey.holdempoker.MyApplication;
import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.model.Joueur;
import com.example.alemariey.holdempoker.model.JoueurManager;
import com.example.alemariey.holdempoker.outils.Outils;

import static com.example.alemariey.holdempoker.outils.Outils.chargePolices;

public class AddJetonsActivity extends AppCompatActivity {
    private Joueur joueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jetons);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        joueur = (Joueur)intent.getSerializableExtra("joueur");
        stylise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_jetons, menu);
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
        Intent intent = new Intent(AddJetonsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void stylise() {
        TextView txtPseudo = (TextView)findViewById(R.id.txtPseudoShop);
        TextView txtNbJeton = (TextView)findViewById(R.id.txtNbJetonShop);

        txtPseudo.setText(joueur.getPseudo()+":");
        txtNbJeton.setText(joueur.getNbJeton()+" Jetons" );

        TextView titreShop = (TextView) findViewById(R.id.titreShop);
        TextView lbl5 = (TextView) findViewById(R.id.lbl5);
        TextView lbl10 = (TextView) findViewById(R.id.lbl10);
        TextView lbl15 = (TextView) findViewById(R.id.lbl15);
        TextView lblAcheterjetons = (TextView) findViewById(R.id.sousTitreAddJetons);
        Button btn1000 = (Button) findViewById(R.id.btn1000);
        Button btn2001 = (Button) findViewById(R.id.btn2001);
        Button btn3003 = (Button) findViewById(R.id.btn3003);

        chargePolices(this, titreShop, MyApplication.getRIOGRANDE());
        chargePolices(this, txtNbJeton, MyApplication.getANDERSON());
        chargePolices(this, txtPseudo, MyApplication.getRIOGRANDE());
        chargePolices(this, lblAcheterjetons, MyApplication.getRIOGRANDE());
        chargePolices(this, lbl5, MyApplication.getRIOGRANDE());
        chargePolices(this, lbl10, MyApplication.getRIOGRANDE());
        chargePolices(this, lbl15, MyApplication.getRIOGRANDE());
        chargePolices(this, btn1000, MyApplication.getANDERSON());
        chargePolices(this, btn2001, MyApplication.getANDERSON());
        chargePolices(this, btn3003, MyApplication.getANDERSON());


        titreShop.setTextColor(ContextCompat.getColor(this, R.color.white));
        lbl5.setTextColor(ContextCompat.getColor(this, R.color.red));
        lbl10.setTextColor(ContextCompat.getColor(this, R.color.red));
        lbl15.setTextColor(ContextCompat.getColor(this, R.color.red));
        txtPseudo.setTextColor(ContextCompat.getColor(this, R.color.white));
        txtNbJeton.setTextColor(ContextCompat.getColor(this, R.color.lightblue));


    }

    public void clique_shop1000(View v){
        addJetonsToJoueur(1000);
    }
    public void clique_shop2001(View v){
        addJetonsToJoueur(2001);
    }
    public void clique_shop3003(View v){
        addJetonsToJoueur(3003);
    }

    private void addJetonsToJoueur(final int nbJeton){
        String dialTitre = "Confirmer la transaction";
        String msg = "";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(dialTitre);
        alertDialog.setMessage(msg);
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String msgToast = "la transaction n'a pas été effectuée";
                Outils.makeToast(MyApplication.getAppContext(),msgToast);
            }
        });
        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                joueur.addJeton(nbJeton);
                JoueurManager.upDateJoueur(joueur);
                String msgToast = joueur.getPseudo()+" a été credité de "+nbJeton+" jetons.";
                Outils.makeToast(MyApplication.getAppContext(), msgToast);
                restartActivity();
            }
        });

        // Set the Icon for the Dialog
//        alertDialog.setIcon(R.drawable.ic_launcher_web);
        alertDialog.setCancelable(false);

        alertDialog.show();

    }

    private void restartActivity(){
        Intent intent = new Intent(AddJetonsActivity.this, AddJetonsActivity.class);
        intent.putExtra("joueur", joueur);
        startActivity(intent);
    }

    public void clique_retourHome(View v){
        Intent intent = new Intent(AddJetonsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
