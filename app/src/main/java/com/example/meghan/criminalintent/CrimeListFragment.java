package com.example.meghan.criminalintent;

import android.content.Intent; //added by page 193 for summoning another fragemnt
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//page 191, to animate change of crime 0 to 5;
//mRecyclerView.getAdapter().notifyItemMoved(0, 5);

/**
 * Created by Meghan on 10/1/2016.
 */
// page 174, 175
public class CrimeListFragment  extends Fragment { //page 181

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle"; //page 254, for saving subtitle visability

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    private boolean mSubtitleVisible; //page 251, Adding a varible to keep track of subtitle visibility

    @Override //page 244, 245 This is responsible for calling Fragment.onCreateOptionsMenu(Menu, MenuInflater)
               // when the activity receives its onCreateOptionsMenu(_) callback from the OS.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view); //Only responablilty is recycling textviews of list
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //RecyclerView need a layoutManager to work
        //LayoutManager handles the positions of items and scrolling behavior

        if (savedInstancesState != null) { //page 254, for saving subtitle visibility
            mSubtitleVisible = savedInstancesState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override //page 201
    public void onResume() { //onResume() is used to update the RecycleView
        super.onResume(); //onResume is perferred over onStart() because the list will not be reloaded if every paused
        updateUI(); //should add any changes to the list.
    }

    @Override //page 254, subtitle should appear in recreated views
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override //page 244, when
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //when you call MenuInflater.inflate(int, menu) and pass in the resource ID of your menu file. This populates
            //the menu instance with the items defined in your file
        super.onCreateOptionsMenu(menu, inflater); //superclass implementation, that way any fuctionality defined by the superclass will still work.
        inflater.inflate(R.menu.fragment_crime_list, menu); //page 244,

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle); //page 252, 251, used to show and hide the subtitle
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override //page 248
    public boolean onOptionsItemSelected(MenuItem item) { //getting Toolbar menu to bring up where you write in crimes
        switch (item.getItemId()) {//page 248
            case R.id.menu_item_new_crime:
                Crime crime = new Crime(); //Creating a new crime
                CrimeLab.get(getActivity()).addCrime(crime);  //adding the crime to the list
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true; //Should return true to show no further processing is necessary //page 248
            case R.id.menu_item_show_subtitle: //page 251, callin the method when the user presses the new action item.
                mSubtitleVisible = !mSubtitleVisible; //page 252, used to show and hide subtitle in toolbar.
                getActivity().invalidateOptionsMenu(); //page 252
                updateSubtitle(); //page 251
                return true;
            default://page 248
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() { //page 250, The subtitle will display the number of crimes in CriminalIntent
        CrimeLab crimeLab = CrimeLab.get(getActivity());  //page 250
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount); //first generates the subtitle string using getString(int resId, Object_
                // formateArgs) method, which accepts replacement values for ethe placeholders in the string resource.  //page 250

        if (!mSubtitleVisible) { //page 252, To make subtitle disappear.
            subtitle = null;
        }

        //page 250, Next, the activity that is hosing the CrimeListFragment is cast ot an AppCompatActivity. CriminalIntent uses the AppCompat library
        //so all activities will be subclass of AppCompatActivity which allow you to access the toolbar (also know as "action bar" for legacy reasons).
        AppCompatActivity activity = (AppCompatActivity) getActivity();  //page 250
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() { //Sets up CrimeListFragment's user interface, page 183, 184
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) { //page 201
            mAdapter = new CrimeAdapter(crimes); //page 183, 184
            mCrimeRecyclerView.setAdapter(mAdapter); //page 183, 184
        } else {
            mAdapter.setCrimes(crimes); //page 271, If the back button is press should still keep data.
            mAdapter.notifyDataSetChanged(); //for changes made
        }

        updateSubtitle(); //page 253, will prevent the text from disappearing when returning to CrimeListFragment.
    }

    private class CrimeHolder extends RecyclerView.ViewHolder //code for list, page 182
            implements View.OnClickListener { //page 191
        //ViewHolder defined here

        //public TextView mTitleTextView; //removed with new layout from page 187
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); //page 191

            //mTitleTextView = (TextView) itemView; //removed with new layout from page 187
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view); //page 188
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            //The above prevent the findViewById from searching through the whole program wasting memory, allows Viewholder to flourish
        }

        public void bindCrime(Crime crime) { //page 189 When given a Crime, CrimeHolder will now update the title TextView, date TextView
            // and solved CheckBox to reflect the state of the Crime.
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override //page 191, CrimeHolder is set as the reciever for Click events
        public void onClick(View v) {
            //Starting an activity from a fragment works similar to starting an activity from another activity,
            //You call the Fragment.startActivity(Intent) method, which calls the corresponding Activity method behind the scenes
            //Intent intent = new Intent(getActivity(), CrimeActivity.class); //page 193 Will start up the CrimeActivity.java
            //startActivity(intent);

            //Removed and replaced by above
            //Toast.makeText(getActivity(),//page 191, CrimeHolder is set as the reciever for Click events
            //      mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
            //    .show();

            //Creating a new Intent method, page 195 //crime ID is now safely stashed in the intent that belongs to CrimeActivity
            //However it is CrimeFragment class that needs to retrieve and use the data
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId()); //getActivity method to access the CrimeActivity's intent directly
            //startActivity(intent);

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId()); //page 209
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> { //page 182
        //RecyclerView will communicate with teh adapter when a ViewHolder object needs to be created or connected with Crime object
        //RecyclerView will not know anything about Crime object, but the adapter will know all Crime's intimate and personal details

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        //implementing three methods in CrimeAdapter, page 183
        @Override //onCreateViewHolder called by the RecyclerView, when it needs a new view to display an item
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false); //changed page 188, former below
            //View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            //You inflate view by calling simple_list_item_1, contains a single textView that looks nice in a list
            return new CrimeHolder(view);
        }

        @Override //It receives the ViewHolder and positions in your data set, page 183
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position); //to find right model data
            //holder.mTitleTextView.setText(crime.getTitle()); //to reflect that data in the model
            holder.bindCrime(crime); //page 189 removed above line added this one
        }

        @Override //page 183
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes) { //Page 271, to refresh the view of CrimeLab too.
            mCrimes = crimes;
        }
    }
}
