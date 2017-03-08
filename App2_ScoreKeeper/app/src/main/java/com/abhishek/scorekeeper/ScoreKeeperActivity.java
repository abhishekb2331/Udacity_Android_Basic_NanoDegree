package com.abhishek.scorekeeper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoreKeeperActivity extends AppCompatActivity implements View.OnClickListener{

    private ScoreKeeperFragment mFrag1;
    private ScoreKeeperFragment mFrag2;
    private FragmentManager fm;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeper);
        Button mReset = (Button) findViewById(R.id.Reset);
        mReset.setOnClickListener(this);
        Bundle mBundle1 =  new Bundle();
        Bundle mBundle2 = new Bundle();
        mBundle1.putString("mTeamLabel","Team A");
        mBundle2.putString("mTeamLabel","Team B");
        mFrag1 = ScoreKeeperFragment.instance(mBundle1);
        mFrag2 = ScoreKeeperFragment.instance(mBundle2);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.TeamA,mFrag1);
        ft.add(R.id.TeamB,mFrag2);
        ft.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Reset:
                mFrag1 = (ScoreKeeperFragment) fm.findFragmentById(R.id.TeamA);
                mFrag2 = (ScoreKeeperFragment) fm.findFragmentById(R.id.TeamB);
                mFrag1.reset();
                mFrag2.reset();
                break;
        }
    }
}
