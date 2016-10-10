package com.example.meghan.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.meghan.criminalintent.CrimeDbSchema.CrimeTable; //page 263, 264, used ALT + Enter to inport on CrimeTable

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Meghan on 10/1/2016.
 */
//This is a singleton; a singleton is a class that allow only one instance of itself to be created
    //Not for longterm memory storage, keeps the crime data available throughout any lifecycle changes in activities and fragments
    //This is an easy way to pass the data between controller classes, page 168, 169
public class CrimeLab {
    private static CrimeLab sCrimeLab; //static variable, private constructor means Other classes will not be able to create a Crimelab (Bypasses get() method)

    //Removed page 263, for database
    //private List<Crime> mCrimes; //page 170, list is an interface that supports an ordered list of objects

    private Context mContext; //page 259
    private SQLiteDatabase mDatabase; //page 259

    public static CrimeLab get(Context context) { //page 170
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }


    private CrimeLab(Context context) { //page 171

        mContext = context.getApplicationContext();// page 259, Opening a SQLiteDatabase
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase(); //When calling getWritableDatabase() here, CrimeBaseHelper will
           // 1. Open up /data/data/com.example.meghan.android.criminalintent/databases/crimeBase.db creating a new Database if one does not exist
          // 2. If this is the first time the database has been created, call onCreate(SQLiteDatabase), then save the latest version.
         // 3. If it's not the first time, check the version number in the database. If the version number in CrimeOpenHelper is higher, call onUpgrade(SQLiteDatebase, int, int).

        //Removed page 263, for database
        //mCrimes = new ArrayList<>(); //regular Java array to store the list elements //page 171

        /* //removed on page 247, because can now add crimes to list
        for (int i = 0; i < 100; i++) { //model layer, adding 100 crimes to list //page 171
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i); //Had 1, caused error for all to be 1. need to have "i".
            crime.setSolved(i % 2 == 0); //Everyother crime is solved in testing code
            mCrimes.add(crime);
        }
        */
    }

    //The user presses the Plus sysmbol in toolbar, they can add the crime themselves
    public void addCrime(Crime c) { //page 247, a new instances of a crime.
        ContentValues values = getContentValues(c); //page 264
        //CrimeTable.NAME is the table you want to insert into, the last argument is the data you want to put in
        //The null (nullColumnHack), you call an insert with an empty ContentValues, SQLite does not allow this, so your insert(_) call would fail.
        mDatabase.insert(CrimeTable.NAME, null, values); //page 264


        //Removed page 263, for database
        //mCrimes.add(c); //page 247
    }

    public List<Crime> getCrimes() { //page 171
        //page 263
        //return mCrimes; //page 171

       // return new ArrayList<>(); //page 263 Should help return a database info

        //page 269, To successfully query the database, so CrimePagerActivity can see all the Crimes in CrimeLab
        List<Crime> crimes = new ArrayList<>(); //page 269

        CrimeCursorWrapper cursor = queryCrimes(null, null); //page 269, Database cursors are called cursors because they always have their finger on a particular place in the query.

        try { //page 269
            cursor.moveToFirst(); //moveToFirst(), Puts cursor at first element
            while (!cursor.isAfterLast()) { // isAfterLast(), tells your pointer is off the end of the dataset
                crimes.add(cursor.getCrime()); //reading a row of data
                cursor.moveToNext(); // moveToNext(), advance to next row
            }
        } finally {
            cursor.close(); //Always close your cursor or will cause error or crash
        }

        return crimes; //page 269
    }

    public Crime getCrime(UUID id) { //page 171

        //page 263
        /*
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        */

        //return null; //page 171
        //page 270, CrimeLab.getCrime(UUID) works, too, so each CrimeFragment displayed in CrimePagerActivity is showing the real Crime.
        CrimeCursorWrapper cursor = queryCrimes( //page 270
                CrimeTable.Cols.UUID + " = ? ",
                new String[] { id.toString() }
        );

        try { //page 270
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) { //page 265
        String uuidString = crime.getId().toString(); //pass in the table name you want
        ContentValues values = getContentValues(crime); //and the values

        mDatabase.update(CrimeTable.NAME, values, //To help the values be saved.
                CrimeTable.Cols.UUID + " = ?", //use question mark so if you pass in string value, it might change your code
                new String[] {uuidString} );

    }

    private static ContentValues getContentValues(Crime crime) { //page 264
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString()); //page 263, 264, used ALT + Enter to inport on CrimeTable, import com.example.meghan.criminalintent.CrimeDbSchema.CrimeTable;
        values.put(CrimeTable.Cols.TITLE, crime.getTitle()); //Every column is here except for _id, which is automaticlly created for you
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1: 0);

        return values;
    }

    //To insert crimes, so the code that adds Crime to CrimeLab when you press the New Crime
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) { //page 269, With CrimeCursorWrapper, vending out a List<Crimes> from CrimeLab will be straightforward.
    //private Cursor queryCrimes(String whereClause, String[] whereArgs) { //page 267, using query(...) in a convenience method to call this on your CrimeTable.
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //Columns - null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy //page 267
        );

        //return cursor; //page 267
        return new CrimeCursorWrapper(cursor); //page 269
    }
}
