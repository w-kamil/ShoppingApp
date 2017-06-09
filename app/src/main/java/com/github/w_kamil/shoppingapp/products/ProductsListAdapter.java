package com.github.w_kamil.shoppingapp.products;


import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;

import java.util.Collections;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.MyViewHolder> {

    private List<Product> productList = Collections.emptyList();
    private OnSingleProductMenuItemClickListner onSingleProductMenuItemClickListner;

    public void setOnSingleProductMenuItemClickListner(OnSingleProductMenuItemClickListner onSingleProductMenuItemClickListner) {
        this.onSingleProductMenuItemClickListner = onSingleProductMenuItemClickListner;
    }

    public ProductsListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product_on_list, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ProductsListAdapter.MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productDescription.setText(product.getDescription());
        holder.productBarcode.setText(String.format(holder.productBarcode.getContext().getString(R.string.barcode_value), product.getBarCode()));
        holder.eventButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.eventButton.getContext(), holder.eventButton);
            onSingleProductMenuItemClickListner.setSearchedProduct(product);
            popupMenu.inflate(R.menu.single_product_popup_menu);
            popupMenu.setOnMenuItemClickListener(onSingleProductMenuItemClickListner);
            popupMenu.show();
        });
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
            productDescription = (TextView) itemView.findViewById(R.id.product_description_textview);
            productBarcode = (TextView) itemView.findViewById(R.id.products_barcode_textview);
            eventButton = (ImageView) itemView.findViewById(R.id.event_button);
        }
    }
}
