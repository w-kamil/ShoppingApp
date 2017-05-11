package com.github.w_kamil.shoppingapp.shops;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }


    public ShopsListAdapter(List<Shop> shopsList) {
        this.shopsList = shopsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_shop_on_list, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = shopsList.get(position);
        holder.shopIdentifier.setText(shop.getIdentifier());
        holder.shopAddress.setText(shop.getAddress());
        holder.eventButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.eventButton.getContext(), holder.eventButton);
            popupMenu.inflate(R.menu.single_shop_popup_menu);
            popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
            popupMenu.show();
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
