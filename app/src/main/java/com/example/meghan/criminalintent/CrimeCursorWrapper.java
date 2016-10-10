package com.example.meghan.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.meghan.criminalintent.CrimeDbSchema.CrimeTable; //Alt plus Enter

import java.util.Date;
import java.util.UUID;

/**
 * Created by Meghan on 10/10/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper { //page 267, code will be used each time data needs to be read from the database
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() { //page 268, getCrime() method that pulls our relevant column data
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID)); //ALT + ENTER on CrimeTable
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED)); //page 268

        Crime crime = new Crime(UUID.fromString(uuidString)); //page 268
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        return crime; //page 268
        //return null; //page 268
    }
}
