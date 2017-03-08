package com.abhishek.habittracker.model;

import android.provider.BaseColumns;

public class HabitTrackerContract {

    public HabitTrackerContract(){};
    public static final String DATABASE_NAME="com.abhishek.habittracker.db";
    public static final int DATABASE_VERSION=1;
    public class HabitTrackerEntry implements BaseColumns
    {
        public static final String TABLE_NAME="HabitTrackerEntry";
        public static final String _ID= BaseColumns._ID;
        public static final String HABIT = "Habit";
        public static final String DATE = "Date";
        public static final String DESC = "Desc";

        public static final int HABIT_WALK_DOG=1;
        public static final int HABIT_MEDICATION=2;
        public static final int HABIT_LEARN=3;
        public static final int HABIT_WALK=4;
    }
}
