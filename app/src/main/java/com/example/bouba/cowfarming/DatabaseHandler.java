package com.example.bouba.cowfarming;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Milking";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String today_rec = "CREATE TABLE Today_rec " +
                "( Bon TEXT PRIMARY KEY , " +
                "Date TEXT, " +
                "Morning TEXT, " +
                "Evening TEXT, " +
                "Total TEXT, " +
                "Earned TEXT, " +
                "Nb_cow TEXT, " +
                "Average TEXT ) ";

        String cow = "CREATE TABLE Cows " +
                "( Mat TEXT PRIMARY KEY , " +
                "Date_nais TEXT, " +
                "Gender TEXT, " +
                "Pic INTEGER , " +
                "Note TEXT) ";
        String cattle = "CREATE TABLE Cattles " +
                "( Mat TEXT PRIMARY KEY , " +
                "Date_nais TEXT, " +
                "Gender TEXT, " +
                "Note TEXT, " +
                "Pic INTEGER ) ";

        db.execSQL(today_rec);
        db.execSQL(cow);
        db.execSQL(cattle);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS Today_rec";
        db.execSQL(sql);
        onCreate(db);
    }

}