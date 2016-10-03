package com.example.meghan.criminalintent;

/**
 * Created by Meghan on 10/2/2016.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment; //page 219
import android.support.v7.app.AlertDialog; //page 219
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//FragmentManager of the hosting activity calls this method as part of putting the DialogFragment on screen
public class DatePickerFragment extends DialogFragment { //page 219

    public static final String EXTRA_DATE = "com.example.meghan.criminalintnet.date"; //page 228

    private static final String ARG_DATE = "date"; //page 225

    private DatePicker mDatePicker; //page 225

    public static DatePickerFragment newInstance(Date date) { //page 225
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment(); //page 225
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) { //should create a Dialog pop-up window //page 219
        Date date = (Date) getArguments().getSerializable(ARG_DATE); //page 226
        //Creating a calender object for day, month and year
        Calendar calendar = Calendar.getInstance(); //page 226
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //page 222 inflating the view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker); //page 226
        mDatePicker.init(year, month, day, null);  //CrimeFragment is successfully telling DatePickerFragment what date to show

        return new AlertDialog.Builder(getActivity()) //AlertDialog.Builder methods to configure you dialog //page 219
                .setView(v) //page 222, setting the view in dialog
                .setTitle(R.string.date_picker_title) //page 219
                //.setPositiveButton(android.R.string.ok, null) //The .setPositiveButton method accepts a string resource and an
                //object that implments DialogInterface.onClickListener, ok and null for listener parameter
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { //page 229
                    @Override //retrieve the date from DatePicker and send the results back to CrimeFragment
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear(); //page 229
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) { //page 228
        if (getTargetFragment() == null ) { //retrieve the date from the DatePicker and send the results back to CrimeFragment
            return;
        }

        Intent intent = new Intent(); //page 228
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent); //page 228
    }

}
