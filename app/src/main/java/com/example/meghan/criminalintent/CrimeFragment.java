package com.example.meghan.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox; //make sure you choose the compoundButton version
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;


/**
 * Created by Meghan on 9/19/2016.
 */
public class CrimeFragment extends Fragment { //Is a controller that interacts with model and view objects
    //Its job is to present the details of a specific crime and update those details as teh user changes them
    //In GeoQuiz most work done by activity.java methods in this Fragments will do most the work
    private Crime mCrime; //page 139
    private EditText mTitleField; //page 141
    private Button mDateButton; //page 153
    private CheckBox mSolvedCheckBox; //153

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        mCrime = new Crime(); //page 139
    }

    @Override //page 140
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title); //page 141
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        //Disable button till chapter 12?
        mDateButton = (Button)v.findViewById(R.id.crime_date); //Page 153
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        //reference for checkbox, page 154
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });

        /*
        Button landscapeOnlyButton =  (Button)v.findViewById(R.id.landscapeOnlyButton);
        if (landscapeOnlyButton != null) {
            //Set it up
        }
        */

        return v;
    }
}
