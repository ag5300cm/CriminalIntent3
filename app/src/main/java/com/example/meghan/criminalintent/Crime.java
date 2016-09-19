package com.example.meghan.criminalintent;

import java.util.UUID;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;


/**
 * Created by Meghan on 9/18/2016.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
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
}
