package com.abhishek.quizapp;

import android.content.Context;

import org.parceler.Parcel;

import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class TextQuestion extends Question {

    private int mQuestionId;
    private int mQuestionImageId;
    private int mAnswerId;

    @Override
    public int QuestionType() {
        return Question.Text;
    }

    public TextQuestion()
    {
        mQuestionImageId=Question.IllegalResource;
    }

    @Override
    public void setAnswer(List<Integer> id) {
        mAnswerId=id.get(0);
    }

    @Override
    public void setIsOptionImage(boolean isOptionImage) {

    }

    @Override
    public boolean getIsOptionImage() {
        return false;
    }

    @Override
    public void setQuestion(int id) {
        mQuestionId=id;
    }

    @Override
    public void setOption1(int id) {

    }

    @Override
    public void setOption2(int id) {

    }

    @Override
    public void setOption3(int id) {

    }

    @Override
    public void setOption4(int id) {

    }

    @Override
    public void setImage(int id) {
        mQuestionImageId=id;
    }

    @Override
    public int getQuestion() {
        return mQuestionId;
    }

    @Override
    public int getOption1() {
        return Question.IllegalResource;
    }

    @Override
    public int getOption2() {
        return Question.IllegalResource;
    }

    @Override
    public int getOption3() {
        return Question.IllegalResource;
    }

    @Override
    public int getOption4() {
        return Question.IllegalResource;
    }

    @Override
    public int getImage() {
        return mQuestionImageId;
    }

    @Override
    public boolean checkAnswer(int id) {
        return false;
    }

    @Override
    public boolean checkAnswer(List<Integer> id) {
        return false;
    }

    @Override
    public boolean checkAnswer(Context mContext,String answer) {
        String mAnswer= mContext.getResources().getString(mAnswerId);
        if(mAnswer.equalsIgnoreCase(answer))
            return true;
        return false;
    }
}
