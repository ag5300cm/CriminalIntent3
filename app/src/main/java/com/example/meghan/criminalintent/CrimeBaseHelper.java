package com.example.meghan.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.meghan.criminalintent.CrimeDbSchema.CrimeTable; //page 260

/** Building Your Initial Database
 * 1. Check to see if the database already exists
 * 2. If it does not, create it and create the tables and initial data it needs.
 * 3. If it does, open it up and see what version of your CrimeDbSchema it has
 * 4. If it is an old version, run code to upgrade it to a newer version *
 */

//Page 262, simple trick to delete the app is quickest way to get rid of database.

//page 259, This class is designed to get rid of the grunt work of opening a SQLiteDatabase, Use it in CrimeLab to create my crime database
public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override //page 259
    public void onCreate(SQLiteDatabase db) {
        //page 260, 261, place cursor on CrimeTable and press ALT + ENTER,
        // (ALT + ENTER), add import for import com.example.meghan.criminalintent.CrimeDbSchema.CrimeTable; //page 260
        db.execSQL("create table" + CrimeTable.NAME + "(" + //page 260, 261
                "_id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED +
                ")"
                );



    }

    @Override //page 259
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
