package com.abhishek.quizapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

public class QuizActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private List<Question> mQuestion;
    private ViewPager mViewPager;
    private VpPagerAdapter mVpPagerAdapter;
    private FloatingActionButton mFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestion = new ArrayList<Question>();

        Question newQuestion;
        List<Integer> mAnswers;
        mAnswers = new ArrayList<Integer>();
        newQuestion = Question.instance(Question.RadioBtn);
        newQuestion.setQuestion(R.string.Question1);
        newQuestion.setOption1(R.string.Question1_Option1);
        newQuestion.setOption2(R.string.Question1_Option2);
        newQuestion.setOption3(R.string.Question1_Option3);
        newQuestion.setOption4(R.string.Question1_Option4);
        mAnswers.add(R.string.Question1_Option3);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.RadioBtn);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setQuestion(R.string.Question2);
        newQuestion.setOption1(R.string.Question2_Option1);
        newQuestion.setOption2(R.string.Question2_Option2);
        newQuestion.setOption3(R.string.Question2_Option3);
        newQuestion.setOption4(R.string.Question2_Option4);
        mAnswers.add(R.string.Question2_Option4);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.CheckBox);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setQuestion(R.string.Question3);
        newQuestion.setOption1(R.string.Question3_Option1);
        newQuestion.setOption2(R.string.Question3_Option2);
        newQuestion.setOption3(R.string.Question3_Option3);
        newQuestion.setOption4(R.string.Question3_Option4);
        mAnswers.add(R.string.Question3_Option1);
        mAnswers.add(R.string.Question3_Option2);
        mAnswers.add(R.string.Question3_Option3);
        mAnswers.add(R.string.Question3_Option4);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.CheckBox);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setQuestion(R.string.Question4);
        newQuestion.setOption1(R.string.Question4_Option1);
        newQuestion.setOption2(R.string.Question4_Option2);
        newQuestion.setOption3(R.string.Question4_Option3);
        newQuestion.setOption4(R.string.Question4_Option4);
        mAnswers.add(R.string.Question4_Option1);
        mAnswers.add(R.string.Question4_Option2);
        mAnswers.add(R.string.Question4_Option3);
        mAnswers.add(R.string.Question4_Option4);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.RadioBtn);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setQuestion(R.string.Question5);
        newQuestion.setImage(R.drawable.udacity);
        newQuestion.setOption1(R.string.Question5_Option1);
        newQuestion.setOption2(R.string.Question5_Option2);
        newQuestion.setOption3(R.string.Question5_Option3);
        newQuestion.setOption4(R.string.Question5_Option4);
        mAnswers.add(R.string.Question5_Option1);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.Text);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setQuestion(R.string.Question6);
        mAnswers.add(R.string.Question6_Option1);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        newQuestion = Question.instance(Question.RadioBtn);
        mAnswers = new ArrayList<Integer>();
        newQuestion.setIsOptionImage(true);
        newQuestion.setQuestion(R.string.Question7);
        newQuestion.setOption1(R.drawable.facebook);
        newQuestion.setOption2(R.drawable.icon);
        newQuestion.setOption3(R.drawable.playstore);
        newQuestion.setOption4(R.drawable.pocket);
        mAnswers.add(R.drawable.playstore);
        newQuestion.setAnswer(mAnswers);
        mQuestion.add(newQuestion);

        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mFAB = (FloatingActionButton) findViewById(R.id.fab);

        mVpPagerAdapter = new VpPagerAdapter(getSupportFragmentManager(),mQuestion);
        mViewPager.setAdapter(mVpPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        springIndicator.setViewPager(mViewPager);

        mFAB.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this,"You are attempting Question "+ String.valueOf(position+1),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int count = mVpPagerAdapter.getCount();
        int score=0;
        for(int i=0;i<count;i++) {
            score += (((QuizFragment) mVpPagerAdapter.getItem(i)).checkAnswer() == true ? 1 : 0);
        }

        Toast.makeText(this,"Your Score is "+ String.valueOf(score),Toast.LENGTH_SHORT).show();
    }
}
