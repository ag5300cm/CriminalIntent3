package com.example.meghan.criminalintent;

//import android.app.FragmentManager; //Replace by below thanks to Teacher Clara suggestion
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity; //Page 128 of Android Programming, This is from Word Document
import android.support.v4.app.Fragment;

//

// public class CrimeActivity extends AppCompatActivity { //original code
public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager(); //page 142, maybe code below correct over book code?
        //android.support.v4.app.FragmentManager fm = getSupportFragmentManager();


        Fragment fragment = fm.findFragmentById(R.id.fragment_container); // page 143

        if (fragment == null) { //page 143
            fragment = new CrimeFragment();
            fm.beginTransaction() //code commits a fragment transaction, used to add/remove/attach/detach/ or replace fragments in fragment list
                    .add(R.id.fragment_container, fragment) //Code commits a fragment transaction
                    //.add method, has two parameters a container view ID and the newly created CrimeFragment.
                    //The container view id is the resource ID fo the FrameLayout that you defined in activity_crime.xml
                    .commit();
        }
    }

}
