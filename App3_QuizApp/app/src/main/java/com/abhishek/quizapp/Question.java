package com.abhishek.quizapp;

import android.content.Context;

import org.parceler.Parcel;

import java.util.List;

public abstract class Question {

    public static final int CheckBox = 1;
    public static final int RadioBtn = 2;
    public static final int Text = 3;
    public static final int IllegalResource = -1;

    public static Question instance(int type)
    {
        switch (type)
        {
            case CheckBox:
                return new CheckBoxQuestion();
            case RadioBtn:
                return new RadioBtnQuestion();
            case Text:
                return new TextQuestion();
        }
        return null;
    }

    public abstract int QuestionType();
    public abstract void setIsOptionImage(boolean isOptionImage);
    public abstract boolean getIsOptionImage();
   // public abstract void setAnswer(int id);
    public abstract void setAnswer(List<Integer> id);
    public abstract void setQuestion(int id);
    public abstract void setOption1(int id);
    public abstract void setOption2(int id);
    public abstract void setOption3(int id);
    public abstract void setOption4(int id);
    public abstract void setImage(int id);
    public abstract int getQuestion();
    public abstract int getOption1();
    public abstract int getOption2();
    public abstract int getOption3();
    public abstract int getOption4();
    public abstract int getImage();
    public abstract boolean checkAnswer(int id);
    public abstract boolean checkAnswer(List<Integer> id);
    public abstract boolean checkAnswer(Context mContext,String answer);
}
