package com.github.w_kamil.shoppingapp.shops;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Shop;

import java.util.Collections;
import java.util.List;


public class ShopsListAdapter extends RecyclerView.Adapter<ShopsListAdapter.MyViewHolder> {

    private List<Shop> shopsList = Collections.emptyList();

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

    public ShopsListAdapter(List<Shop> shopsList) {
        this.shopsList = shopsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.sigle_shop, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = shopsList.get(position);
        MyViewHolder myViewHolder = holder;
        myViewHolder.shopIdentifier.setText(shop.getIdentifier());
        myViewHolder.shopAddress.setText(shop.getAddress());
        myViewHolder.eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.eventButton.getContext(),myViewHolder.eventButton);
                popupMenu.inflate(R.menu.single_shop_menu);
                popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopIdentifier;
        TextView shopAddress;
        ImageView eventButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            shopIdentifier = (TextView) itemView.findViewById(R.id.shop_identifier_textview);
            shopAddress = (TextView) itemView.findViewById(R.id.shop_address_textview);
            eventButton = (ImageView) itemView.findViewById(R.id.event_button);

        }

    }
}
