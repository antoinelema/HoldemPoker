package com.example.dbn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alemariey.holdempoker.HomeActivity;
import com.example.alemariey.holdempoker.MyApplication;
import com.example.alemariey.holdempoker.R;
import com.example.alemariey.holdempoker.model.JoueurManager;
import com.example.alemariey.holdempoker.outils.Outils;


public class AddActivity extends AppCompatActivity {

    private final String TAG = "ADD ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();
        stylise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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

    //-----------------------------------------------methodes applicatives--------------------------

    /**
     * stylise les element visuel
     */
    public void stylise(){
        TextView titreAdd = (TextView) findViewById(R.id.titreAddJoueur);
        TextView lblPseudo = (TextView) findViewById(R.id.lblPseudo);
        Button btnOkAdd = (Button) findViewById(R.id.button_addOk);

        Outils.chargePolices(this,titreAdd, MyApplication.getRIOGRANDE());
        Outils.chargePolices(this,lblPseudo,MyApplication.getANDERSON());
        Outils.chargePolices(this,btnOkAdd,MyApplication.getRIOGRANDE());
    }

    /**
     * prepare l'insert d'un joueur dans la base de donnés
     * @param view
     */
    public void clique_addOk(View view){
        boolean empty = false;
        boolean insert = false;
        String msg = "";
        EditText ETPseudo = (EditText) findViewById(R.id.editTextAddPseudo);

        String pseudo = (ETPseudo.getText().toString()).toUpperCase();

        try{
            if (pseudo.isEmpty()){
                empty = true;
            }else {
                JoueurManager.insertJoueur(pseudo);
                insert = true;
            }

        }catch (Exception e){
            insert = false;
        }

        //verification
        if(empty){
            msg = getResources().getString(R.string.msgAddEmpty);
        }
        else if (insert){
            msg = pseudo +" "+ getResources().getString(R.string.msgAddOK);
            Intent intent = new Intent(AddActivity.this, HomeActivity.class);
            startActivity(intent);
        }else {
            msg = getResources().getString(R.string.msgAddExist);
        }

        Outils.makeToast(this,msg);

    }
}
