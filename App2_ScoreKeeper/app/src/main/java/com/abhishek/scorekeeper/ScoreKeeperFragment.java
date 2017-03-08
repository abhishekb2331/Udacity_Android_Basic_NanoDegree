package com.abhishek.scorekeeper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ScoreKeeperFragment extends Fragment implements View.OnClickListener{


    private TextView mTeamLabel;
    private TextView mScoreLabel;
    private Button mTouchDown;
    private Button mFieldGoal;
    private Button mExtraPoint1;
    private Button mExtraPoint2;
    private Button mSafety;

    public static final int TOUCH_DOWN_POINTS = 6;
    public static final int FIELD_GOAL_POINTS = 3;
    public static final int EXTRA_POINT1_POINTS = 1;
    public static final int EXTRA_POINT2_POINTS = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static final int SAFETY_POINTS = 2;

    private int mScore=0;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.team_layout,container,false);
        mTeamLabel =(TextView) mView.findViewById(R.id.TeamLabel);
        mScoreLabel = (TextView) mView.findViewById(R.id.ScoreLabel);
        mFieldGoal =(Button) mView.findViewById(R.id.FieldGoal);
        mTouchDown = (Button) mView.findViewById(R.id.TouchDown);
        mExtraPoint1 =(Button) mView.findViewById(R.id.ExtraPoint1);
        mExtraPoint2 =(Button) mView.findViewById(R.id.ExtraPoint2);
        mSafety = (Button) mView.findViewById(R.id.Safety);
        Bundle mBundle = getArguments();
        if(mBundle!=null)
        {
            mTeamLabel.setText((String) mBundle.get("mTeamLabel"));
        }
        if(savedInstanceState!=null)
        {
            //mScore = Integer.parseInt((String) getArguments().getString("mScore"));
            //mScoreLabel.setText((String) getArguments().getString("mScore"));
        }
        mFieldGoal.setOnClickListener(this);
        mTouchDown.setOnClickListener(this);
        mExtraPoint2.setOnClickListener(this);
        mExtraPoint1.setOnClickListener(this);
        mSafety.setOnClickListener(this);
        return mView;
    }

    public static ScoreKeeperFragment instance( Bundle args )
    {
        ScoreKeeperFragment mscoreKeeperFragment = new ScoreKeeperFragment();
        mscoreKeeperFragment.setArguments(args);
        return mscoreKeeperFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FieldGoal:
                mScore+=FIELD_GOAL_POINTS;
                display();
                break;
            case R.id.TouchDown:
                mScore+=TOUCH_DOWN_POINTS;
                display();
                break;
            case R.id.ExtraPoint1:
                mScore+=EXTRA_POINT1_POINTS;
                display();
                break;
            case R.id.ExtraPoint2:
                mScore+=EXTRA_POINT2_POINTS;
                display();
                break;
            case R.id.Safety:
                mScore+=SAFETY_POINTS;
                display();
                break;
        }
    }

    public void display()
    {
        mScoreLabel.setText(String.valueOf(mScore));
    }

    public void reset()
    {
        mScore =0;
        display();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mScore",String.valueOf(mScore));
    }
}
