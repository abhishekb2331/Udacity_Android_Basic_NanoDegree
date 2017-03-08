package com.abhishek.quizapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.parceler.Parcels;

public class TextFragment  extends QuizFragment{
    private Question mQuestion;
    private TextView mQuestionText;
    private EditText mAnswerText;
    public static final String QUESTION_KEY = "question";
    public static final String ANSWER_KEY ="answer";
    public static TextFragment instance(Question question)
    {
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(QUESTION_KEY, Parcels.wrap(question));
        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(mBundle);
        return textFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.text_quiz,container,false);
        mQuestionText= (TextView) mView.findViewById(R.id.question);
        mAnswerText = (EditText) mView.findViewById(R.id.answer);
        Bundle mBundle = getArguments();
        mQuestion=null;
        if(mBundle!=null)
        {
            mQuestion=Parcels.unwrap(mBundle.getParcelable(QUESTION_KEY));
            if(mQuestion.getImage()!=Question.IllegalResource)
                mQuestionText.setCompoundDrawablesWithIntrinsicBounds(0,mQuestion.getImage(),0,0);
            mQuestionText.setText(mQuestion.getQuestion());
        }
        if(savedInstanceState!=null)
        {
            mAnswerText.setText(savedInstanceState.getString(ANSWER_KEY));
        }
        return mView;
    }

    public boolean checkAnswer()
    {
        if(mAnswerText!=null)
            return mQuestion.checkAnswer(getActivity().getApplicationContext(),String.valueOf(mAnswerText.getText()));
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            mAnswerText.setText(savedInstanceState.getString(ANSWER_KEY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ANSWER_KEY,mAnswerText.getText().toString());
    }
}
