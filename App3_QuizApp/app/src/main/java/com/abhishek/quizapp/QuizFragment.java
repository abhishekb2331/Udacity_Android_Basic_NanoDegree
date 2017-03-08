package com.abhishek.quizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class QuizFragment extends Fragment {

    public static QuizFragment instance(Question question)
    {
        switch (question.QuestionType())
        {
            case Question.CheckBox:
                return CheckBoxFragment.instance(question);
            case Question.RadioBtn:
                return RadioBtnFragment.instance(question);
            case Question.Text:
                return TextFragment.instance(question);
        }
      //  Log.i("question type",String.valueOf(question.QuestionType()));
        return null;
    }

    public abstract boolean checkAnswer();

}
