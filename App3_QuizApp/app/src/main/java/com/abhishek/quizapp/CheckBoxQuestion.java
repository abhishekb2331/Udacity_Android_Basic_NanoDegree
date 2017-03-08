package com.abhishek.quizapp;


import android.content.Context;
import android.util.Log;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class CheckBoxQuestion extends Question{

    private int mQuestionId;
    private int mQuestionImageId;
    private int mOption1Id;
    private int mOption2Id;
    private int mOption3Id;
    private int mOption4Id;
    private List<Integer> mAnswerList;
    private boolean isOptionImage;

    @Override
    public int QuestionType() {
        return Question.CheckBox;
    }

    public CheckBoxQuestion()
    {
        mQuestionImageId=Question.IllegalResource;
    }

    public void setIsOptionImage(boolean isOptionImage)
    {
        this.isOptionImage = isOptionImage;
    }

    public boolean getIsOptionImage()
    {
        return isOptionImage;
    }

    @Override
    public void setAnswer(List<Integer> id) {
        mAnswerList = id;
    }

    public void setQuestion(int id)
    {
        mQuestionId =id;
    }

    public void setOption1(int id)
    {
        mOption1Id =id;
    }

    public void setOption2(int id)
    {
        mOption2Id =id;
    }

    public void setOption3(int id)
    {
        mOption3Id =id;
    }

    public void setOption4(int id)
    {
        mOption4Id =id;
    }

    public void setImage(int id)
    {
        mQuestionImageId =id;
    }

    public int getQuestion()
    {
        return mQuestionId;
    }

    public int getOption1()
    {
        return mOption1Id;
    }

    public int getOption2()
    {
        return mOption2Id;
    }

    public int getOption3()
    {
        return mOption3Id;
    }

    public int getOption4()
    {
        return mOption4Id;
    }

    public int getImage()
    {
        return mQuestionImageId;
    }

    public boolean checkAnswer(List<Integer> checkid)
    {
        for( int id : checkid)
        {
            if (!mAnswerList.contains(id))
                return false;
        }
        if(checkid.size()== mAnswerList.size())
            return true;
        return false;
    }

    @Override
    public boolean checkAnswer(Context mContext, String answer) {
        return false;
    }

    @Override
    public boolean checkAnswer(int id) {
        return false;
    }
}
