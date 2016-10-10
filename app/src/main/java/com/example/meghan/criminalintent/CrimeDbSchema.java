package com.example.meghan.criminalintent;

/**
 * Created by Meghan on 10/9/2016.
 */
//  database.CrimeDbSchema, where or how do i get this to put the CrimeDbSchema.java in its own database package

public class CrimeDbSchema { //page 258, For the database
    public static final class CrimeTable { //defining an inner class called CrimeTable to discribe your table,
        //The CrimeTable class only exists to define the String constants needed to describe the moving pieces of your table definition
        public static final String NAME = "crimes"; //The first piece is the name of the table in this database CrimeTable.NAME

        //Defining the table columns page 258
        //Will be able to refer to teh column named "title" in a Java-safe way: CrimeTable.Cols.TITLE
        //This is a much safer way to change your program or somethings name, or add additional data to the table
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}


