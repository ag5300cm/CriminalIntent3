package com.example.meghan.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager; //This where I add RootView? page 207

import java.util.List;
import java.util.UUID;

/**
 * Created by Meghan on 10/2/2016.
 */
public class CrimePagerActivity extends FragmentActivity { //superclass is FragmentActivity, page 206, 207

    private static final String EXTRA_CRIME_ID = "com.example.meghan.android.criminalintent.crime_id"; //page 209

    private ViewPager mViewPager; //page 208
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) { //page 209
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //page 207
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID); //page 209

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager); //page 208, finding ViewPager in the activity's view,

        mCrimes = CrimeLab.get(this).getCrimes(); //get the data set from CrimeLab (the list of crimes).
        FragmentManager fragmentManager = getSupportFragmentManager(); //activity's instance of FragmentManager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) { //set adapter to unnamed instance of FragmentStatePagerAdapter
            //FragmentStatePagerAdapter is your agent managing the conversation with ViewPager. For your agent to do its job with
            //the fragments that getItem(int) returns, it needs to be able to add them to your activity. (THis is why you need FragmentManager

            @Override
            public Fragment getItem(int position) {
                //getItem(int) method fetches the Crime instance for the given position in the dataset. Then uses that Crime's ID to create
                // and return a properly configured CrimeFragment
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() { //returns the number of items in the array list
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) { //page 210 should allow you to Select any item and display the details of the correct Crime.
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
