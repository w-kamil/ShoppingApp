package com.github.w_kamil.shoppingapp.products;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.dao.Product;

import java.util.Collections;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.MyViewHolder> {

    private List<Product> productList = Collections.emptyList();
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public ProductsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProductsListAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productDescription;
        TextView productBarcode;
        ImageView eventButton;


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
