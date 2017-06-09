package com.github.w_kamil.shoppingapp.products;

import android.support.v7.widget.PopupMenu;

import com.github.w_kamil.shoppingapp.dao.Product;

public interface OnSingleProductMenuItemClickListner extends PopupMenu.OnMenuItemClickListener {
    void setSearchedProduct(Product product);
}
