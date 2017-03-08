package com.abhishek.inventoryapp.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class InventoryContentProvider extends ContentProvider {
    private InventoryDbHelper inventoryDbHelper;
    private static final int INVENTORY_LIST = 1;
    private static final int INVENTORY_ITEM = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(InventoryContractClass.CONTENT_AUTHORITY, InventoryContractClass.PATH_INVENTORY_ITEM, INVENTORY_LIST);
        uriMatcher.addURI(InventoryContractClass.CONTENT_AUTHORITY, InventoryContractClass.PATH_INVENTORY_ITEM + "/#", INVENTORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        inventoryDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = inventoryDbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case INVENTORY_LIST:
                break;
            case INVENTORY_ITEM:
                selection = InventoryContractClass.INVENTORY_ITEM._ID + " =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("Illegal Query");
        }
        cursor = db.query(InventoryContractClass.INVENTORY_ITEM.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case INVENTORY_LIST:
                return InventoryContractClass.INVENTORY_ITEM.CONTENT_LIST_TYPE;
            case INVENTORY_ITEM:
                return InventoryContractClass.INVENTORY_ITEM.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case INVENTORY_LIST:
                int validate = input_validate(values);
                switch (validate) {
                    case 1:
                        SQLiteDatabase db = inventoryDbHelper.getWritableDatabase();
                        long id = db.insert(InventoryContractClass.INVENTORY_ITEM.TABLE_NAME, null, values);
                        if (id == -1)
                            return null;
                        getContext().getContentResolver().notifyChange(uri, null);
                        return ContentUris.withAppendedId(uri, id);
                }
            default:
                throw new IllegalArgumentException("Illegal Insertion");
        }
    }

    private int input_validate(ContentValues values) {
        String name = values.getAsString(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME);
        int price = values.getAsInteger(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE);
        if (name == null) {
            throw new IllegalArgumentException("Enter a Valid name");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Enter a Valid price");
        }
        return 1;
    }

    private int update_validate(ContentValues values) {
        if(values.containsKey(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME))
        {
            String name = values.getAsString(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Enter a Valid name");
            }
        }
        if(values.containsKey(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE))
        {
            int price = values.getAsInteger(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE);
            if (price < 0) {
                throw new IllegalArgumentException("Enter a Valid price");
            }
        }
        return 1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = inventoryDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rows_deleted;
        switch (match) {
            case INVENTORY_LIST:
                break;
            case INVENTORY_ITEM:
                selection = InventoryContractClass.INVENTORY_ITEM._ID + " =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("Illegal Query");
        }
        rows_deleted = db.delete(InventoryContractClass.INVENTORY_ITEM.TABLE_NAME, selection, selectionArgs);
        if (rows_deleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rows_deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = inventoryDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rows_updated = 0;
        if (values == null)
            throw new IllegalArgumentException("Values to be updated can't be null");
        int validate = update_validate(values);
        switch (validate) {
            case 1:
                break;
            default:
                throw new IllegalArgumentException("Illegal Update");
        }
        switch (match) {
            case INVENTORY_LIST:
                break;
            case INVENTORY_ITEM:
                selection = InventoryContractClass.INVENTORY_ITEM._ID + " =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
                throw new IllegalArgumentException("Illegal Insertion");
        }
        rows_updated = db.update(InventoryContractClass.INVENTORY_ITEM.TABLE_NAME, values,selection,selectionArgs);
        if (rows_updated != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rows_updated;
    }
}
