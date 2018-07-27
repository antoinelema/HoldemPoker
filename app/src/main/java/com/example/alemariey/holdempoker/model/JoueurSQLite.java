package com.example.alemariey.holdempoker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by a.lemariey on 24/07/2018.
 * base de donnée de joueurs
 */
public class JoueurSQLite extends SQLiteOpenHelper {
    private static final String TAG = "JoueurSQLite";
    private static final String DATABASE_NAME = "Joueurs.db"; //nom de la base
    private static final int DATABASE_VERSION = 5; //version de la base

    public static final String TABLE_JOUEURS = "joueurs"; //nom de la table
    public static final String COL_PSEUDO = "pseudo";
    public static final String COL_JETONS = "nbJetons";

    private static volatile JoueurSQLite instance = null;

    //requete create bdd
    public static final String CREATE_TABLE_JOUEURS =
            "CREATE TABLE " + TABLE_JOUEURS + " (" +
                    COL_PSEUDO + " TEXT PRIMARY KEY, " +
                    COL_JETONS + " INT);";

    //requetes de peuplement
    public static final String INSERT_ANTOINE = "INSERT INTO " + TABLE_JOUEURS + " (pseudo,nbJetons) VALUES ('ANTOINE','1000');";
    public static final String INSERT_PAUL = "INSERT INTO " + TABLE_JOUEURS + " (pseudo,nbJetons) VALUES ('PAUL','1000');";

    public static final String DROP_TABLE_JOUEURS = "DROP TABLE IF EXISTS " + TABLE_JOUEURS + ";";

    /**
     * constructeur singleton
     * @param context
     */
    private JoueurSQLite(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     *
     * @param context
     * @return l'instance de la bdd
     */
    public static JoueurSQLite getInstance(Context context) {

        if (JoueurSQLite.instance == null) {
            Log.i("BASE", "Créer instance Convert");
            JoueurSQLite.instance = new JoueurSQLite(context);
        }
        return JoueurSQLite.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate bdd Joueurs");
        db.execSQL(CREATE_TABLE_JOUEURS);

        // Peuplement de la base
        Log.i(TAG, "Peuplement de la base");
        db.execSQL(INSERT_ANTOINE);
        db.execSQL(INSERT_PAUL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL(DROP_TABLE_JOUEURS);
        onCreate(db);
    }
}
