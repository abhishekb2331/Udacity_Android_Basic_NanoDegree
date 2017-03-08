package com.abhishek.quizapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxFragment extends QuizFragment {

    private Question mQuestion;
    private TextView mQuestionText;
    private AppCompatCheckBox mOption1;
    private AppCompatCheckBox mOption2;
    private AppCompatCheckBox mOption3;
    private AppCompatCheckBox mOption4;
    private List<Integer> mOptionSelected;
    public static final String QUESTION_KEY = "question";
    public static CheckBoxFragment instance(Question question)
    {
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(QUESTION_KEY, Parcels.wrap(question));
        CheckBoxFragment checkBoxFragment = new CheckBoxFragment();
        checkBoxFragment.setArguments(mBundle);
        return checkBoxFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.checkbox_quiz,container,false);
        mQuestionText= (TextView) mView.findViewById(R.id.question);
        mOption1= (AppCompatCheckBox) mView.findViewById(R.id.option1);
        mOption2= (AppCompatCheckBox) mView.findViewById(R.id.option2);
        mOption3= (AppCompatCheckBox) mView.findViewById(R.id.option3);
        mOption4= (AppCompatCheckBox) mView.findViewById(R.id.option4);

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
        return mView;
    }

    public boolean checkAnswer()
    {
        mOptionSelected = new ArrayList<Integer>();
        if(mOption1!=null && mOption1.isChecked())
        {
            mOptionSelected.add(mQuestion.getOption1());
        }
        if(mOption2!=null && mOption2.isChecked())
        {
            mOptionSelected.add(mQuestion.getOption2());
        }
        if(mOption3!=null && mOption3.isChecked())
        {
            mOptionSelected.add(mQuestion.getOption3());
        }
        if(mOption4!=null && mOption4.isChecked())
        {
            mOptionSelected.add(mQuestion.getOption4());
        }
        if(mOptionSelected.size()>0)
        {
            return mQuestion.checkAnswer(mOptionSelected);
        }
        return false;
    }
}
