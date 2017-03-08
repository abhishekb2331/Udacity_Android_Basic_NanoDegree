package com.abhishek.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.inventoryapp.adapter.InventoryAdapter;
import com.abhishek.inventoryapp.model.InventoryContractClass;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.OnSaleClickListner,LoaderManager.LoaderCallbacks<Cursor>{

    private InventoryAdapter inventoryAdapter;
    private static final int LOADER_INIT=1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ListView listView = (ListView) findViewById(R.id.list);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_text);
        listView.setEmptyView(emptyTextView);
        inventoryAdapter = new InventoryAdapter(this,null);
        inventoryAdapter.setOnSaleClickListner(this);
        listView.setAdapter(inventoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri= ContentUris.withAppendedId(InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,id);
                Intent intent = new Intent(InventoryActivity.this,InventoryEditor.class);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.FAB);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this,InventoryEditor.class);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(LOADER_INIT,null,this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_dummy_data:
                add_dummy_data();
                return true;
            case R.id.delete_all:
                delete_all();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSale(int id,int quantity,int sale) {
        int Quantity = quantity;
        int Sale = sale;
        int _ID = id;
        if(Quantity>0)
        {
            Quantity--;
            Sale++;
            ContentValues contentValues = new ContentValues();
            contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY,Quantity);
            contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_ITEM_SOLD,Sale);
            Uri uri = ContentUris.withAppendedId(InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,_ID);
            getContentResolver().update(uri,contentValues,null,null);
        }
        else
        {
            Toast.makeText(this,"Out Of Stock",Toast.LENGTH_SHORT).show();
        }
    }

    public void add_dummy_data()
    {
        String Title = "New Product";
        int Price = 100;
        int Quantity = 50;
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME,Title);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE,Price);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY,Quantity);
        getContentResolver().insert(InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,contentValues);
    }

    public void delete_all()
    {
        FragmentManager fm = getSupportFragmentManager();
        final AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance("Delete All Record","Are You Sure You Want To Delete All Record?"
                ,"Yes","No");
        DialogInterface.OnClickListener positiveDialogClickListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContentResolver().delete(InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,null,null);
            }
        };
        DialogInterface.OnClickListener negativeDialogClickListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogFragment.dismiss();
            }
        };
        alertDialogFragment.setPositiveButtonClickListener(positiveDialogClickListner);
        alertDialogFragment.setNegativeButtonClickListener(negativeDialogClickListner);
        alertDialogFragment.show(fm,"fragment_dialog");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projection = {
          InventoryContractClass.INVENTORY_ITEM._ID,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_NAME,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_PHONE,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_EMAIL,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_IMAGE,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_DESCRIPTION,
                InventoryContractClass.INVENTORY_ITEM.COLUMN_ITEM_SOLD
        };
        return new CursorLoader(this,InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,Projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        inventoryAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        inventoryAdapter.swapCursor(null);
    }
}
