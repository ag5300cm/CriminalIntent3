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
}
