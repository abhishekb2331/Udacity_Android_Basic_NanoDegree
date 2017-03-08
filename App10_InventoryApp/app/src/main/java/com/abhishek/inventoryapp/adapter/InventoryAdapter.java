package com.abhishek.inventoryapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.inventoryapp.R;
import com.abhishek.inventoryapp.model.InventoryContractClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class InventoryAdapter extends CursorAdapter{
    private OnSaleClickListner onSaleClickListner;
    public InventoryAdapter(Context context,Cursor cursor)
    {
        super(context,cursor,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        String Title = cursor.getString(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_NAME));
        int Sale = cursor.getInt(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_ITEM_SOLD));
        String Price = "$"+cursor.getInt(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_PRICE));
        int Quantity = cursor.getInt(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_QUANTITY));
        String img=cursor.getString(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM.COLUMN_IMAGE));
        if(!img.equalsIgnoreCase("No images"))
        {
            Glide.with(context).load(img).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.getImage());
        }
        else
        {
            Glide.with(context).load(R.drawable.android).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.getImage());
        }
        viewHolder.getImage_Sale().setImageResource(R.drawable.add);
        viewHolder.getTitle().setText(Title);
        viewHolder.getSale().setText(""+Sale);
        viewHolder.getPrice().setText(Price);
        viewHolder.getQuantity().setText(""+Quantity);
        viewHolder.set_id(cursor.getInt(cursor.getColumnIndex(InventoryContractClass.INVENTORY_ITEM._ID)));
        view.setTag(viewHolder);
        viewHolder.getImage_Sale().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSaleClickListner!=null) {
                    int quantity=Integer.valueOf(viewHolder.getQuantity().getText().toString().trim());
                    int sale= Integer.valueOf(viewHolder.getSale().getText().toString().trim());
                    onSaleClickListner.onSale(viewHolder.get_id(),
                            quantity,
                            sale);
                }
            }
        });
    }

    public void setOnSaleClickListner(OnSaleClickListner onSaleClickListner) {
        this.onSaleClickListner = onSaleClickListner;
    }

    public interface OnSaleClickListner
    {
        public void onSale(int id,int quantity,int sale);
    }
    private static class ViewHolder
    {
        int _id;
        ImageView Image;
        TextView Title;
        TextView Sale;
        TextView Price;
        TextView Quantity;
        ImageView Image_Sale;
        public ViewHolder(View view)
        {
            Image = (ImageView) view.findViewById(R.id.image);
            Title = (TextView) view.findViewById(R.id.title);
            Sale = (TextView) view.findViewById(R.id.sale);
            Price = (TextView) view.findViewById(R.id.price);
            Quantity = (TextView) view.findViewById(R.id.quantity);
            Image_Sale = (ImageView) view.findViewById(R.id.image_sale);
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public int get_id() {
            return _id;
        }

        public ImageView getImage() {
            return Image;
        }

        public TextView getPrice() {
            return Price;
        }

        public TextView getQuantity() {
            return Quantity;
        }

        public TextView getSale() {
            return Sale;
        }

        public TextView getTitle() {
            return Title;
        }

        public ImageView getImage_Sale() {
            return Image_Sale;
        }
    }
}
