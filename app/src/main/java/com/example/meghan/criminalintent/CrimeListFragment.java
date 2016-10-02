package com.example.meghan.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view); //Only responablilty is recycling textviews of list
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //RecyclerView need a layoutManager to work
        //LayoutManager handles the positions of items and scrolling behavior

        updateUI();

        return view;
    }

    private void updateUI() { //Sets up CrimeListFragment's user interface, page 183, 184
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
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
            Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
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
    }
}
