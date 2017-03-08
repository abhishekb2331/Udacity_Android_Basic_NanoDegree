package com.abhishek.inventoryapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abhishek.inventoryapp.model.InventoryContractClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryEditor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> ,View.OnTouchListener{
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 100;
    private static final int OPEN_IMAGE_REQUEST_CODE = 101;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_NAME_REGEX= Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern VALID_PHONE_REGEX = Pattern.compile("^\\d{10}$");
    private static final Pattern VALID_PRICE_REGEX = Pattern.compile("^\\d+$");
    private static final Pattern VALID_QUANTITY_REGEX= Pattern.compile("^\\d+$");
    private static final Pattern VALID_SALE_REGEX= Pattern.compile("^\\d+$");
    private static final Pattern VALID_DESCRIPTION_REGEX=Pattern.compile(".+");
    private static final Pattern VALID_TITLE_REGEX=Pattern.compile(".+");

    private boolean mContentChanged=false;
    private boolean mEditMode = true;
    private Uri mUri=null;
    private String ImageUri =null;
    private ImageView Image;
    private EditText Title;
    private EditText Description;
    private EditText Price;
    private EditText Quantity;
    private EditText Sold;
    private EditText Supplier_Name;
    private EditText Supplier_Phone;
    private EditText Supplier_Email;
    private static final int LOADER_INIT=1;

    private TextWatcher TitletextWatcher;
    private TextWatcher DescriptiontextWatcher;
    private TextWatcher PricetextWatcher;
    private TextWatcher QuantitytextWatcher;
    private TextWatcher SoldtextWatcher;
    private TextWatcher Supplier_NametextWatcher;
    private TextWatcher Supplier_PhonetextWatcher;
    private TextWatcher Supplier_EmailtextWatcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mUri = intent.getData();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Image = (ImageView) findViewById(R.id.image);
        Title = (EditText) findViewById(R.id.title);
        Description = (EditText) findViewById(R.id.description);
        Price = (EditText) findViewById(R.id.price);
        Quantity = (EditText) findViewById(R.id.quantity);
        Sold = (EditText) findViewById(R.id.sold);
        Supplier_Name = (EditText) findViewById(R.id.supplier_name);
        Supplier_Phone = (EditText) findViewById(R.id.supplier_phone);
        Supplier_Email = (EditText) findViewById(R.id.supplier_email);

        TitletextWatcher= new ValidationTextWatcher(Title,VALID_TITLE_REGEX);
        DescriptiontextWatcher= new ValidationTextWatcher(Description,VALID_DESCRIPTION_REGEX);
        PricetextWatcher= new ValidationTextWatcher(Price,VALID_PRICE_REGEX);
        QuantitytextWatcher=new ValidationTextWatcher(Quantity,VALID_QUANTITY_REGEX);
        SoldtextWatcher=new ValidationTextWatcher(Sold,VALID_SALE_REGEX);
        Supplier_NametextWatcher=new ValidationTextWatcher(Supplier_Name,VALID_NAME_REGEX);
        Supplier_EmailtextWatcher=new ValidationTextWatcher(Supplier_Email,VALID_EMAIL_ADDRESS_REGEX);
        Supplier_PhonetextWatcher=new ValidationTextWatcher(Supplier_Phone,VALID_PHONE_REGEX);


        setListeners();
        if(mUri==null)
        {
            mEditMode=false;
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.delete).setVisible(false);
            menu.findItem(R.id.order).setVisible(false);
            bottomNavigationView.postInvalidate();
            setTitle("Insert Data");
        }
        else
        {
            mEditMode=true;
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.delete).setVisible(true);
            menu.findItem(R.id.order).setVisible(true);
            setTitle("Edit Data");
            getSupportLoaderManager().initLoader(LOADER_INIT,null,this);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.add_data:
                        ContentValues contentValues = getValues();
                        add_data(contentValues);
                        return true;
                    case R.id.delete:
                        delete_data();
                        return true;
                    case R.id.order:
                        order_data();
                        return true;
                    default:
                        return false;
                }
            }
        });
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndOpenImage();
                mContentChanged=true;
            }
        });
    }

    private class ValidationTextWatcher implements TextWatcher{

        private EditText mEditText;
        private Pattern mPattern;
        public ValidationTextWatcher(EditText editText,Pattern pattern)
        {
            mEditText=editText;
            mPattern=pattern;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mContentChanged=true;
            Matcher matcher = mPattern.matcher(s);
            if(matcher.find())
            {
                Drawable drawable_left = mEditText.getCompoundDrawables()[0];
                Drawable drawable_right = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_check_black_24dp,null);
                mEditText.setCompoundDrawablesWithIntrinsicBounds(drawable_left,null,drawable_right,null);
            }
            else
            {
                Drawable drawable_left = mEditText.getCompoundDrawables()[0];
                Drawable drawable_right = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_clear_black_24dp,null);
                mEditText.setCompoundDrawablesWithIntrinsicBounds(drawable_left,null,drawable_right,null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private void checkAndOpenImage()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE_REQUEST_CODE);
        openImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openImage();
                }
                break;
        }
    }
    private void openImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case OPEN_IMAGE_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    ImageUri = data.getData().toString();
                    //Toast.makeText(this,"added image"+ImageUri,Toast.LENGTH_SHORT).show();
                    Glide.with(this).load(ImageUri).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(Image);
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setListeners()
    {
        Title.addTextChangedListener(TitletextWatcher);
        Description.addTextChangedListener(DescriptiontextWatcher);
        Price.addTextChangedListener(PricetextWatcher);
        Quantity.addTextChangedListener(QuantitytextWatcher);
        Sold.addTextChangedListener(SoldtextWatcher);
        Supplier_Name.addTextChangedListener(Supplier_NametextWatcher);
        Supplier_Email.addTextChangedListener(Supplier_EmailtextWatcher);
        Supplier_Phone.addTextChangedListener(Supplier_PhonetextWatcher);
    }
    private void removeListeners()
    {
        Title.removeTextChangedListener(TitletextWatcher);
        Description.removeTextChangedListener(DescriptiontextWatcher);
        Price.removeTextChangedListener(PricetextWatcher);
        Quantity.removeTextChangedListener(QuantitytextWatcher);
        Sold.removeTextChangedListener(SoldtextWatcher);
        Supplier_Name.removeTextChangedListener(Supplier_NametextWatcher);
        Supplier_Email.removeTextChangedListener(Supplier_EmailtextWatcher);
        Supplier_Phone.removeTextChangedListener(Supplier_PhonetextWatcher);
    }
    private ContentValues getValues()
    {
        ContentValues contentValues = new ContentValues();
        String title = Title.getText().toString();
        String description = Description.getText().toString();
        String price = Price.getText().toString();
        String quantity = Quantity.getText().toString();
        String sold = Sold.getText().toString();
        String supplier_name= Supplier_Name.getText().toString();
        String supplier_phone= Supplier_Phone.getText().toString();
        String supplier_email= Supplier_Email.getText().toString();
        Matcher titleMatcher;
        Matcher descriptionMatcher;
        Matcher priceMatcher;
        Matcher quantityMatcher;
        Matcher soldMatcher;
        Matcher supplier_nameMatcher;
        Matcher supplier_phoneMatcher;
        Matcher supplier_emailMatcher;
        if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(description)|| TextUtils.isEmpty(supplier_name)||TextUtils.isEmpty(supplier_phone)|| TextUtils.isEmpty(supplier_email)
                || TextUtils.isEmpty(price)|| TextUtils.isEmpty(quantity)||TextUtils.isEmpty(sold) || ImageUri==null) {
            titleMatcher=VALID_TITLE_REGEX.matcher(title);
            descriptionMatcher= VALID_DESCRIPTION_REGEX.matcher(description);
            priceMatcher=VALID_PRICE_REGEX.matcher(price);
            quantityMatcher=VALID_QUANTITY_REGEX.matcher(quantity);
            soldMatcher=VALID_SALE_REGEX.matcher(sold);
            supplier_nameMatcher=VALID_NAME_REGEX.matcher(supplier_name);
            supplier_phoneMatcher=VALID_PHONE_REGEX.matcher(supplier_phone);
            supplier_emailMatcher=VALID_EMAIL_ADDRESS_REGEX.matcher(supplier_email);
            if(!titleMatcher.find() || !descriptionMatcher.find() || !priceMatcher.find() || !quantityMatcher.find()
                    || !soldMatcher.find() || !supplier_nameMatcher.find() || !supplier_emailMatcher.find() || !supplier_phoneMatcher.find()){

                Toast.makeText(this,"One or more fields are invalid",Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME,title);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_DESCRIPTION,description);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE,Integer.valueOf(price));
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY,Integer.valueOf(quantity));
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_ITEM_SOLD,Integer.valueOf(sold));
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_NAME,supplier_name);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_PHONE,supplier_phone);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_EMAIL,supplier_email);
        contentValues.put(InventoryContractClass.INVENTORY_ITEM.COLUMN_IMAGE,ImageUri);
        return contentValues;
    }
    private void add_data(ContentValues contentValues)
    {
        if(contentValues==null)
            return;
        if(mEditMode)
        {
            int num_rows_updated=0;
            num_rows_updated=getContentResolver().update(mUri,contentValues,null,null);
            if(num_rows_updated>0)
                Toast.makeText(this,"Update Successful",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Update Unsuccessful",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Uri uri=getContentResolver().insert(InventoryContractClass.INVENTORY_ITEM.CONTENT_URI,contentValues);
            if(uri!=null)
            {
                Toast.makeText(this,"Insert Successful",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Insert Unsuccessful",Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
    private void delete_data()
    {
        if(mEditMode)
        {
            FragmentManager fm = getSupportFragmentManager();
            final AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance("Delete Record","Are You Sure You Want To Delete This Record?"
                    ,"Yes","No");
            DialogInterface.OnClickListener positiveDialogClickListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int num_rows_deleted=0;
                    num_rows_deleted=getContentResolver().delete(mUri,null,null);
                    if(num_rows_deleted>0)
                        Toast.makeText(InventoryEditor.this,"Delete Successful",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(InventoryEditor.this,"Delete Unsuccessful",Toast.LENGTH_SHORT).show();
                    finish();
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
        else
        {
            Toast.makeText(this,"Cannot Perform This Operation",Toast.LENGTH_SHORT).show();
        }
    }
    private void order_data()
    {
        if(mEditMode)
        {
            FragmentManager fm = getSupportFragmentManager();
            final AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance("Order From Supplier","Place An Order Via Phone Or Email"
                    ,"Phone","Email");
            DialogInterface.OnClickListener positiveDialogClickListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse("smsto:"+Supplier_Phone.getText().toString().trim());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", "Please Send Supply Of "+ Title.getText().toString().trim());
                    startActivity(intent);
                }
            };
            DialogInterface.OnClickListener negativeDialogClickListner = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto",Supplier_Email.getText().toString().trim(), null));
                   // intent.setType("text/plain");
                    //intent.setData(Uri.parse("mailto:" + Supplier_Email.getText().toString().trim()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Request For Order");
                    intent.putExtra(Intent.EXTRA_TEXT, "Please Send Supply Of "+ Title.getText().toString().trim());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };
            alertDialogFragment.setPositiveButtonClickListener(positiveDialogClickListner);
            alertDialogFragment.setNegativeButtonClickListener(negativeDialogClickListner);
            alertDialogFragment.show(fm,"fragment_dialog");
        }
        else {
            Toast.makeText(this,"Cannot Perform This Operation",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(!mContentChanged)
            super.onBackPressed();

        FragmentManager fm = getSupportFragmentManager();
        final AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance("Go Back","You Have Unsaved Changes Do You Really Want To Go Back?"
        ,"Yes","No");
        DialogInterface.OnClickListener positiveDialogClickListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v("InventoryEditor",""+mContentChanged+" "+ item.getItemId() +" home: "+R.id.home);
        if(!mContentChanged)
            return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                final AlertDialogFragment alertDialogFragment = AlertDialogFragment.instance("Go Back","You Have Unsaved Changes Do You Really Want To Go Back?"
                        ,"Yes","No");
                DialogInterface.OnClickListener positiveDialogClickListner = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(InventoryEditor.this);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        return new CursorLoader(this,mUri,Projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null|| data.getCount()==0)
            return;
        data.moveToFirst();
        removeListeners();
        Title.setText(data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME)));
        Description.setText(data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_DESCRIPTION)));
        Price.setText(""+data.getInt(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE)));
        Quantity.setText(""+data.getInt(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY)));
        Sold.setText(""+data.getInt(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_ITEM_SOLD)));
        Supplier_Name.setText(data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_NAME)));
        Supplier_Phone.setText(data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_PHONE)));
        Supplier_Email.setText(data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_SUPPLIER_EMAIL)));
        String img=data.getString(data.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_IMAGE));
        if(!img.equalsIgnoreCase("No images"))
        {
            Glide.with(this).load(img).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(Image);
        }
        setListeners();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mContentChanged=true;
        return true;
    }
}
