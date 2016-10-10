package com.example.meghan.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.Date;
import java.util.UUID;


/**
 * Created by Meghan on 9/19/2016.
 */
public class CrimeFragment extends Fragment { //Is a controller that interacts with model and view objects
    //Its job is to present the details of a specific crime and update those details as teh user changes them
    //In GeoQuiz most work done by activity.java methods in this Fragments will do most the work
    private Crime mCrime; //page 139

    private static final String ARG_CRIME_ID = "crime_id"; //page 198
    private static final String DIALOG_DATE = "DialogDate"; // page 220, adding a constant for the DatePickerFragment's tag.

    private static final int REQUEST_DATE = 0; //page 227, creating a constant

    private EditText mTitleField; //page 141
    private Button mDateButton; //page 153
    private CheckBox mSolvedCheckBox; //153

    /* Example of a Bundle arguments example, page 198
    Bundle args = new Bundle();
    args.putSerializable(EXTRA_MY_OBJECT, myObject);
    args.putInt(EXTRA_MY_INT, myInt);
    args.putCharSequesnce(EXTRA_MY_STRING, myString);
    */

    public static CrimeFragment newInstance(UUID crimeId) {
        //page 198, Should call CrimeFragment newInstance(UUID) when it needs to create a CrimeFragment
        //It will pass the UUID it retrieved from its extra. Return to CrimeActivity and, in createFragment(), retrieve the extra from CrimeActivity's
        //intent and pass it into CrimeFragment.newInstance(UUID), can also make EXTRA_CRIME_ID private since no other class will access that extra
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        //mCrime = new Crime(); //page 139
        //removed above for below, page 196
        //getIntent() method returns that Intent that was used to start CrimeActivity
        // You call the getSerializableExtra(String) on the Intent to pull the UUID out into a variable
        //UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);//page 199 removed above line
        //For above, When a fragment needs ot acces its arguments, it calls the fragment method getArguments() and
        //then one of the type-specific "get" methods of Bundle
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId); //page 196
    }

    @Override //page 265, Crime instances get modified in CrimeFragment, and will need to be written out when CrimeFragment is done.
    public void onPause() { //updates CrimeLab's copy of your Crime.
        super.onPause();

        CrimeLab.get(getActivity())
                .updateCrime(mCrime);
    }

    @Override //page 140
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title); //page 141
        mTitleField.setText(mCrime.getTitle()); //page 196
        mTitleField.addTextChangedListener(new TextWatcher() { //page 141
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
        updateDate(); //page 231, see line below
        //updateDate().setText(mCrime.getDate().toString()); // page 230 Refactored from mDateButton
        //mDateButton.setEnabled(false);
        //removed above, replaced by below
        mDateButton.setOnClickListener(new View.OnClickListener() { //page 220, this shows DatePickerFragment when the date button is pressed
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager(); //page 220
                //DatePickerFragment dialog = new DatePickerFragment(); //page 220
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate()); //page 225
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE); //page 227, Making CrimeFragment the target fragment of the DatePickerFragment instance
                dialog.show(manager, DIALOG_DATE); //page 220
            }
        });

        //reference for checkbox, page 154
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved()); //page 196
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

    @Override //page 229, In CrimeFragment, override onActivityResult(_) to retrieve the extra, set the date on the Crime,
                            //and refresh the text of the date button.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate(); //was formally mDateButton.setText(mCrime.getDate().toString());
        }
    }

    private void updateDate() {
        //return mDateButton; //page 230, 231
        mDateButton.setText(mCrime.getDate().toString()); //removed return part
    }

}
