package com.example.meghan.criminalintent;

import android.content.Context;

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

    private List<Crime> mCrimes; //page 170, list is an interface that supports an ordered list of objects

    public static CrimeLab get(Context context) { //
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }


    private CrimeLab(Context context) { //page 171
        mCrimes = new ArrayList<>(); //regular Java array to store the list elements
        for (int i = 0; i < 100; i++) { //model layer, adding 100 crimes to list
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i); //Had 1, caused error for all to be 1. need to have "i".
            crime.setSolved(i % 2 == 0); //Everyother crime is solved in testing code
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
