package com.abhishek.inventoryapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="inventory_item.db";
    private static final int DATABASE_VERSION=1;
    public InventoryDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InventoryContractClass.INVENTORY_ITEM.CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ InventoryContractClass.INVENTORY_ITEM.TABLE_NAME);
        onCreate(db);
    }
}
