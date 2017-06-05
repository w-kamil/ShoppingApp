package com.github.w_kamil.shoppingapp.singleProduct;


import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Shopping;

import java.util.Collections;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyVieHolder> {

    private List<Shopping> shoppingList = Collections.emptyList();
    LayoutInflater layoutInflater;
    private OnSingleShoppingMenuItemClickListener onSingleShoppingMenuItemClickListener;

    public void setOnSingleShoppingMenuItemClickListener(OnSingleShoppingMenuItemClickListener onSingleShoppingMenuItemClickListener) {
        this.onSingleShoppingMenuItemClickListener = onSingleShoppingMenuItemClickListener;
    }

    public ShoppingListAdapter(List<Shopping> shoppingList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.shoppingList = shoppingList;
    }

    @Override
    public ShoppingListAdapter.MyVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.single_shopping_on_list, parent, false);
        return new MyVieHolder(layout);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.MyVieHolder holder, int position) {
        Shopping shopping = shoppingList.get(position);
        Log.d("zakup do listy", shopping.getProduct().getBarCode().toString());
        holder.productName.setText(shopping.getProduct().getDescription());
        holder.productBarcode.setText(shopping.getProduct().getBarCode());
        holder.shopNameAndAddress.setText(shopping.getShop().getIdentifier() + ", " + shopping.getShop().getAddress());
        holder.date.setText(shopping.getDate().toString());
        holder.price.setText(shopping.getPrice().toString() + " " + holder.price.getContext().getString(R.string.currency));
        holder.eventButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.eventButton.getContext(), holder.eventButton);
            onSingleShoppingMenuItemClickListener.setShoppingEntry(shopping);
            popupMenu.inflate(R.menu.single_shopping_menu);
            popupMenu.setOnMenuItemClickListener(onSingleShoppingMenuItemClickListener);
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public class MyVieHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView productBarcode;
        TextView shopNameAndAddress;
        TextView date;
        TextView price;
        ImageView eventButton;


        public MyVieHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name_textview);
            productBarcode = (TextView) itemView.findViewById(R.id.product_barcode_textview);
            shopNameAndAddress = (TextView) itemView.findViewById(R.id.shop_name_and_address_textview);
            date = (TextView) itemView.findViewById(R.id.date_textview);
            price = (TextView) itemView.findViewById(R.id.price_textview);
            eventButton = (ImageView) itemView.findViewById(R.id.event_button);
        }
    }
}
