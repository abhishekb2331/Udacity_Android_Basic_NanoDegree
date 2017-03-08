package com.abhishek.inventoryapp.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class InventoryContractClass {

    public static final String CONTENT_AUTHORITY="com.abhishek.inventoryapp";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY_ITEM="inventory_item";

    public static final class INVENTORY_ITEM implements BaseColumns {
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_INVENTORY_ITEM);
        public static final String CONTENT_LIST_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+CONTENT_AUTHORITY+"/"+PATH_INVENTORY_ITEM;
        public static final String CONTENT_ITEM_TYPE= ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_INVENTORY_ITEM;
        public static final String TABLE_NAME="inventory";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_QUANTITY="quantity";
        public static final String COLUMN_SUPPLIER_NAME="supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE="supplier_phone";
        public static final String COLUMN_SUPPLIER_EMAIL="supplier_email";
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_ITEM_SOLD="item_sold";
        public static final String CREATE_INVENTORY_TABLE= "CREATE TABLE "+ TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL,"
                + COLUMN_PRICE + " TEXT NOT NULL DEFAULT 0,"
                + COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0,"
                + COLUMN_SUPPLIER_NAME + " TEXT NOT NULL DEFAULT 'new name',"
                + COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL DEFAULT '9999999999',"
                + COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL DEFAULT 'ab@abc.com',"
                + COLUMN_IMAGE + " TEXT NOT NULL DEFAULT 'No images',"
                + COLUMN_DESCRIPTION + " TEXT NOT NULL DEFAULT 'New Product ',"
                + COLUMN_ITEM_SOLD + " INTEGER NOT NULL DEFAULT 0 "
                + ");";
    }
}
