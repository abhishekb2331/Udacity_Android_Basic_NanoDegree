package com.abhishek.quizapp;

import android.content.Context;

import org.parceler.Parcel;

import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class RadioBtnQuestion  extends  Question{

    private int mQuestionId;
    private int mQuestionImageId;

    @Override
    public int QuestionType() {
        return Question.RadioBtn;
    }

    @Override
    public boolean checkAnswer(List<Integer> id) {
        return false;
    }

    @Override
    public boolean checkAnswer(Context mContext, String answer) {
        return false;
    }

    private int mOption1Id;
    private int mOption2Id;
    private int mOption3Id;
    private int mOption4Id;
    private int mAnswerId;
    private boolean isOptionImage;

    public RadioBtnQuestion()
    {
        mQuestionImageId=Question.IllegalResource;
    }

    @Override
    public void setIsOptionImage(boolean isOptionImage) {
        this.isOptionImage = isOptionImage;
    }

    @Override
    public boolean getIsOptionImage() {
        return isOptionImage;
    }

    @Override
    public void setAnswer(List<Integer> id) {
        mAnswerId=id.get(0);
   }

    @Override
    public void setQuestion(int id) {
        mQuestionId =id;
    }

    @Override
    public void setOption1(int id) {
        mOption1Id =id;
    }

    @Override
    public void setOption2(int id) {
        mOption2Id =id;
    }

    @Override
    public void setOption3(int id) {
        mOption3Id =id;
    }

    @Override
    public void setOption4(int id) {
        mOption4Id =id;
    }

    @Override
    public void setImage(int id) {
        mQuestionImageId =id;
    }

    @Override
    public int getQuestion() {
        return mQuestionId;
    }

    @Override
    public int getOption1() {
        return mOption1Id;
    }

    @Override
    public int getOption2() {
        return mOption2Id;
    }

    @Override
    public int getOption3() {
        return mOption3Id;
    }

    @Override
    public int getOption4() {
        return mOption4Id;
    }

    @Override
    public int getImage() {
        return mQuestionImageId;
    }

    @Override
    public boolean checkAnswer(int id) {
        if(id == mAnswerId)
            return true;
        return false;
    }
}
