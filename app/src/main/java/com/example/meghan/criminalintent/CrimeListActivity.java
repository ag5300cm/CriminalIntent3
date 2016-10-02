package com.example.meghan.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Meghan on 10/1/2016.
 */
//page 174
public class CrimeListActivity extends SingleFragmentActivity {
    //page 175 must be declare in the AndroidManifest.xml

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
