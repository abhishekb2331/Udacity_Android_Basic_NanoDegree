package com.abhishek.quizapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.parceler.Parcels;

public class RadioBtnFragment extends QuizFragment{
    private Question mQuestion;
    private TextView mQuestionText;
    private RadioGroup mOptionGroup;
    private RadioButton mOption1;
    private RadioButton mOption2;
    private RadioButton mOption3;
    private RadioButton mOption4;
    public static final String QUESTION_KEY = "question";
    public static final String OPTION1_KEY="option1";
    public static final String OPTION2_KEY="option2";
    public static final String OPTION3_KEY="option3";
    public static final String OPTION4_KEY="option4";

    public static RadioBtnFragment instance(Question question)
    {
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(QUESTION_KEY, Parcels.wrap(question));
        RadioBtnFragment radioBtnFragment = new RadioBtnFragment();
        radioBtnFragment.setArguments(mBundle);
        return radioBtnFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.radiobtn_quiz,container,false);
        mQuestionText= (TextView) mView.findViewById(R.id.question);
        mOptionGroup = (RadioGroup) mView.findViewById(R.id.optionGroup);
        mOption1= (RadioButton) mView.findViewById(R.id.option1);
        mOption2= (RadioButton) mView.findViewById(R.id.option2);
        mOption3= (RadioButton) mView.findViewById(R.id.option3);
        mOption4= (RadioButton) mView.findViewById(R.id.option4);

        Bundle mBundle = getArguments();
        mQuestion=null;
        if(mBundle!=null)
        {
            mQuestion=Parcels.unwrap(mBundle.getParcelable(QUESTION_KEY));
            if(mQuestion.getImage()!=Question.IllegalResource)
                mQuestionText.setCompoundDrawablesWithIntrinsicBounds(0,mQuestion.getImage(),0,0);
            mQuestionText.setText(mQuestion.getQuestion());
            if(mQuestion.getIsOptionImage())
            {
                mOption1.setCompoundDrawablesWithIntrinsicBounds(0,0,mQuestion.getOption1(),0);
                mOption2.setCompoundDrawablesWithIntrinsicBounds(0,0,mQuestion.getOption2(),0);
                mOption3.setCompoundDrawablesWithIntrinsicBounds(0,0,mQuestion.getOption3(),0);
                mOption4.setCompoundDrawablesWithIntrinsicBounds(0,0,mQuestion.getOption4(),0);
            }
            else {
                mOption1.setText(mQuestion.getOption1());
                mOption2.setText(mQuestion.getOption2());
                mOption3.setText(mQuestion.getOption3());
                mOption4.setText(mQuestion.getOption4());
            }
        }

        if(savedInstanceState!=null)
        {
            mOption1.setChecked(savedInstanceState.getBoolean(OPTION1_KEY,false));
            mOption2.setChecked(savedInstanceState.getBoolean(OPTION2_KEY,false));
            mOption3.setChecked(savedInstanceState.getBoolean(OPTION3_KEY,false));
            mOption4.setChecked(savedInstanceState.getBoolean(OPTION4_KEY,false));
        }
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mOption1!=null)
        {
            outState.putBoolean(OPTION1_KEY, mOption1.isChecked());
        }
        else
            Log.v("CheckBoxFragment","Save option1 is null");
        if(mOption2!=null)
        {
            outState.putBoolean(OPTION2_KEY, mOption2.isChecked());
        }
        else
            Log.v("CheckBoxFragment","Save option2 is null");
        if(mOption3!=null)
        {
            outState.putBoolean(OPTION3_KEY, mOption3.isChecked());
        }
        else
            Log.v("CheckBoxFragment","Save option3 is null");
        if(mOption4!=null)
        {
            outState.putBoolean(OPTION4_KEY, mOption4.isChecked());
        }
        else
            Log.v("CheckBoxFragment","Save option4 is null");
    }
    public boolean checkAnswer()
    {
        if(mOption1!=null && mOption1.isChecked())
        {
            return mQuestion.checkAnswer(mQuestion.getOption1());
        }
        if(mOption2!=null && mOption2.isChecked())
        {
            return mQuestion.checkAnswer(mQuestion.getOption2());
        }
        if(mOption3!=null && mOption3.isChecked())
        {
            return mQuestion.checkAnswer(mQuestion.getOption3());
        }
        if(mOption4!=null && mOption4.isChecked())
        {
            return mQuestion.checkAnswer(mQuestion.getOption4());
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("fragment","Activity Recreated");
        if(savedInstanceState!=null)
        {
            mOption1.setChecked(savedInstanceState.getBoolean(OPTION1_KEY,false));
            mOption2.setChecked(savedInstanceState.getBoolean(OPTION2_KEY,false));
            mOption3.setChecked(savedInstanceState.getBoolean(OPTION3_KEY,false));
            mOption4.setChecked(savedInstanceState.getBoolean(OPTION4_KEY,false));
        }
    }
}
