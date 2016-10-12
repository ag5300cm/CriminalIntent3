package com.example.meghan.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat; //page 279, recommended format
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox; //make sure you choose the compoundButton version
import android.widget.CompoundButton;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
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

    private static final int REQUEST_CONTACT = 1; //page 284,

    private static final int REQUEST_PHOTO = 2; //page 300, For Firing a camera intent

    private File mPhotoFile; //page 298, For Grabbing photo file location

    private EditText mTitleField; //page 141
    private Button mDateButton; //page 153
    private CheckBox mSolvedCheckBox; //153

    private ImageButton mPhotoButton; //294, Instance variables
    private ImageView mPhotoView; //page 294

    private Button mSuspectButton; //page 284

    private Button mReportButton; //page 281, for intent

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

        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime); // 298, Stashing the location of the photo file.
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

        //281, Here you use the Intent constructor that accepts a string that is a constant defining the action.
        mReportButton = (Button) v.findViewById(R.id.crime_report); //page 281, creating a implicit intent to send a crime report
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain"); //Job we want done.
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject)); //CrimeReport is a string, page 281
                i = Intent.createChooser(i, getString(R.string.send_report)); //page 283, Allows user to pick were to send report such as gmail, twitter, Drive, etc
                startActivity(i);
            }
        });

        final  Intent pickContact = new Intent(Intent.ACTION_PICK, //page 284, once a suspected is assigned show the name of the suspect
                ContactsContract.Contacts.CONTENT_URI); //page 284
        //pickContact.addCategory(Intent.CATEGORY_HOME); //Page 287, 288, Dummy code, to verify filter works, but do not have a device with contacts to make list of subjects.
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect); //page 284
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT); //Will be using pickContact one more time, which is why you put it outside mSuspectButton's OnClickListener
            }
        });

        if (mCrime.getSuspect() != null) { //page 284, once a suspected is assigned show the name of the suspect
            mSuspectButton.setText(mCrime.getSuspect());
        }

        //page 287, Some devices or users may not have a contacts app, and if the Operating System cannot find a matching activity, then the app will crash.
        PackageManager packageManager = getActivity().getPackageManager(); //page 287, PackageMananger knows all the components installed on your Android device
        if (packageManager.resolveActivity(pickContact, //By calling the resolveActivity(Intent, int), you ask it to find an activity that matches the Intent you gave it.
                PackageManager.MATCH_DEFAULT_ONLY) == null) { //The MATCH_DEFAULT_ONLY flag restricts this search to activities with the CATEGORY_DEFAULT flag, just like startActivity(Intent) does.
            mSuspectButton.setEnabled(false); //If search successful, will return an instance of ResolveInfo telling you all about which activity it found,
        } //on the other hand, if search returns null, it means no contacts app. So you disable the useless suspect button.

        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera); //page 294, To respond to presses on ImageButton, and to control content of ImageView.

        //299, MediaStore defines the public interfaces used in Android for interactino with common media (images)
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //page 300, Firing a camera intent,

        boolean canTakePhoto = mPhotoFile != null && //page 300
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) { //page 300
            Uri uri = Uri.fromFile(mPhotoFile); //For a full-resolution output, you need to tell it where to save the image on the filesystem.
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri); //This is done by passing a Uri pointing to where you want to save the file in MediaStore.EXTRA_OUTPUT
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() { //page 300
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.crime_photo); //294, call findViewById(int) as usual on your inflated fragment_crime.xml and you will find views from view_camera_and_title.xml too.
        updatePhotoView(); //page 303

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
            updateDate(); //was formally mDateButton.setText(mCrime.getDate().toString()); //page 229
        } else if (requestCode == REQUEST_CONTACT && data != null) { //page 286, Pulling contact name out
            Uri contactUri = data.getData(); //page 286
            // Specify which fields you want your query to return
            //values for.
            String[] queryFields = new String[] { //page 286
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            //Perform your query - the contactUri is like a "where"
            //clause here
            Cursor c = getActivity().getContentResolver() //page 286
                    .query(contactUri, queryFields, null, null, null);
            try { //page 286
                //Double-Check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                //Pull out the first column of the first row of data -
                //that is your suspect's name.
                c.moveToFirst();
                String suspect = c.getString(0); //page 286
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            } finally { //page 286
                c.close();
            }
        } else if (requestCode == REQUEST_PHOTO) { //page 303
            updatePhotoView();
        }

    }

    private void updateDate() {
        //return mDateButton; //page 230, 231
        mDateButton.setText(mCrime.getDate().toString()); //removed return part
    }

    private String getCrimeReport() { //page 279, created four strings and then pieces them together and returns a complete report.
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);

        return report;//page 279
    }

    private void updatePhotoView() { //page 303, Calling the conservative scale method from PictureUtils.java to update Photo View.
        if (mPhotoFile == null  || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

}
