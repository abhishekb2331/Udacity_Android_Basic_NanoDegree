package com.abhishek.habittracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitTrackerHelper extends SQLiteOpenHelper {

    public  HabitTrackerHelper(Context context)
    {
        super(context,HabitTrackerContract.DATABASE_NAME,null, HabitTrackerContract.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable= "CREATE TABLE IF NOT EXISTS "+ HabitTrackerContract.HabitTrackerEntry.TABLE_NAME +
                " (" + HabitTrackerContract.HabitTrackerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HabitTrackerContract.HabitTrackerEntry.DATE + " STRING NOT NULL, " +
                HabitTrackerContract.HabitTrackerEntry.DESC + " STRING NOT NULL, " +
                HabitTrackerContract.HabitTrackerEntry.HABIT+ " INTEGER NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable= "DROP TABLE IF EXISTS "+ HabitTrackerContract.HabitTrackerEntry.TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public void insert(String Date,int Habit,String Desc)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitTrackerContract.HabitTrackerEntry.DATE,Date);
        contentValues.put(HabitTrackerContract.HabitTrackerEntry.DESC,Desc);
        contentValues.put(HabitTrackerContract.HabitTrackerEntry.HABIT,Habit);
        db.insert(HabitTrackerContract.HabitTrackerEntry.TABLE_NAME,null,contentValues);
    }

    public Cursor read()
    {
        String[] mProjection={HabitTrackerContract.HabitTrackerEntry._ID, HabitTrackerContract.HabitTrackerEntry.DATE, HabitTrackerContract.HabitTrackerEntry.DESC, HabitTrackerContract.HabitTrackerEntry.HABIT};
        SQLiteDatabase db= getReadableDatabase();
        return db.query(HabitTrackerContract.HabitTrackerEntry.TABLE_NAME,mProjection,null,null,null,null,null);
    }
}
