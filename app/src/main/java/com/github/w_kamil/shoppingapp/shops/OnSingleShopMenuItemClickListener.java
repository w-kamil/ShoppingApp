package com.github.w_kamil.shoppingapp.shops;

import android.support.v7.widget.PopupMenu;

import com.github.w_kamil.shoppingapp.dao.Shop;


public interface OnSingleShopMenuItemClickListener extends PopupMenu.OnMenuItemClickListener {
    void setSelectedShopEntry(Shop selectedShopEntry);
}
