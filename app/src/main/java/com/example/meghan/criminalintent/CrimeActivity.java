package com.example.meghan.criminalintent;

//import android.app.FragmentManager; //Replace by below thanks to Teacher Clara suggestion
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog; //page 217 get appCompat Dependency File -> Project Structure -> app -> dependences -> com.android.support.appcompat-v7:24:1:0

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity { //gets info from SingleFragmentActivity page 173, 174

    //Creating a new Intent method, page 195
    private static final String EXTRA_CRIME_ID = "com.example.meghan.criminalintent.crime_id"; //page 195, page 199 changed from public to private
    //Creating a new Intent method, page 195 //crime ID is now safely stashed in the intent that belongs to CrimeActivity
    //However it is CrimeFragment class that needs to retrieve and use the data
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {//gets info from SingleFragmentActivity page 173, 174
        //return new CrimeFragment();

        //CrimeActivity needs to know plenty about CrimeFragment, including that is has a newInstance(UUID) method. But Fragments do not need to know about activities
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID); //Page 199,
        return CrimeFragment.newInstance(crimeId);
    }
}

// public class CrimeActivity extends AppCompatActivity { //original code
/* // Removed for new code on page 174, to do with new SingleFragmentActivity.java fragment. so less code needed
public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crime); //renamed page 172
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager(); //page 142, maybe code below correct over book code?
        //android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container); // page 143

        if (fragment == null) { //page 143
            fragment = new CrimeFragment();
            fm.beginTransaction() //code commits a fragment transaction, used to add/remove/attach/detach/ or replace fragments in fragment list
                    .add(R.id.fragment_container, fragment) //Code commits a fragment transaction
                    //.add method, has two parameters a container view ID and the newly created CrimeFragment.
                    //The container view id is the resource ID fo the FrameLayout that you defined in activity_fragment.xmlxml
                    .commit();
        }
    }
}
*/