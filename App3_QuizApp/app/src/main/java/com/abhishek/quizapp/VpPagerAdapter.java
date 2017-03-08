package com.abhishek.quizapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VpPagerAdapter extends FragmentPagerAdapter{

    private List<Question> mQuestionList;
    private List<QuizFragment> mFragmentList;

    public VpPagerAdapter(FragmentManager fragmentManager,List<Question> QuestionList)
    {
        super(fragmentManager);
        mFragmentList= new ArrayList<>();
        mQuestionList=QuestionList;
        setFragments();
    }

    private void setFragments() {
        QuizFragment quizFragment;
        for(Question question : mQuestionList)
        {
            quizFragment= QuizFragment.instance(question);
       //     Log.i("quizFragment is null?",String.valueOf(quizFragment == null) + quizFragment.toString());
            mFragmentList.add((QuizFragment) quizFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ""+(position+1);
    }
}
