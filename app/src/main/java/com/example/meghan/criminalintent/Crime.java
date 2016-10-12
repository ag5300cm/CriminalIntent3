package com.example.meghan.criminalintent;

import java.util.Date;
import java.util.UUID;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;


/**
 * Created by Meghan on 9/18/2016.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate; //The time the crime occured; //Used Option+Return or ALt+Enter to import the date class
    private boolean mSolved; //If the crime is solved
    private String mSuspect; //page 276, Will hold the name of the suspect.

    public Crime() {
        this(UUID.randomUUID());  //page 268

        // Generate unique identifier
        // mId = UUID.randomUUID();
        //mDate = new Date();
    }

    public Crime(UUID id) { //page 268, a constructor to help return the appropriate UUID id
        mId = id;
        mDate = new Date();
    }

    public  UUID getId() {
        return mId;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    //getter and setter for date
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() { //page 276, gets suspect
        return mSuspect;
    }

    public void setSuspect(String suspect) { //page 276, holds the name of the suspect
        mSuspect = suspect;
    }

    //Adding a method to Crime.java to get a well-known filename
    public String getPhotoFilename() { //page 297, Crime.getPhotoFilename() will not know what folder the photo will be stored in.
        return "IMG_" + getId().toString() + ".jpg"; //However, the filename will be unique, since it is based on the Crime's ID
    }

}
