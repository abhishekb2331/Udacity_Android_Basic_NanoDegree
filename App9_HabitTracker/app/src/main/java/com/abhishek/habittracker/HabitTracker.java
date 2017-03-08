package com.abhishek.habittracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.abhishek.habittracker.model.HabitTrackerContract;
import com.abhishek.habittracker.model.HabitTrackerHelper;

public class HabitTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        HabitTrackerHelper habitTrackerHelper = new HabitTrackerHelper(this);
        habitTrackerHelper.insert(getString(R.string.date), HabitTrackerContract.HabitTrackerEntry.HABIT_LEARN,getString(R.string.learn_new));
        habitTrackerHelper.insert(getString(R.string.date), HabitTrackerContract.HabitTrackerEntry.HABIT_MEDICATION,getString(R.string.taking_medicine));
        habitTrackerHelper.insert(getString(R.string.date), HabitTrackerContract.HabitTrackerEntry.HABIT_WALK_DOG,getString(R.string.walk_dog));
        habitTrackerHelper.insert(getString(R.string.date), HabitTrackerContract.HabitTrackerEntry.HABIT_WALK,getString(R.string.walk));
        Cursor cursor = habitTrackerHelper.read();
        String msg,msg2="";
        TextView textView = (TextView) findViewById(R.id.text);
        while (cursor.moveToNext())
        {
            msg = "ID : " + cursor.getInt(cursor.getColumnIndex(HabitTrackerContract.HabitTrackerEntry._ID)) +
                    " Date " + cursor.getString(cursor.getColumnIndex(HabitTrackerContract.HabitTrackerEntry.DATE)) +
                    " Habit " + cursor.getInt(cursor.getColumnIndex(HabitTrackerContract.HabitTrackerEntry.HABIT)) +
                    " Desc " + cursor.getString(cursor.getColumnIndex(HabitTrackerContract.HabitTrackerEntry.DESC))+"\n";
            msg2+=msg;
            Log.v("Database",msg);
        }
        textView.setText(msg2);
    }
}
