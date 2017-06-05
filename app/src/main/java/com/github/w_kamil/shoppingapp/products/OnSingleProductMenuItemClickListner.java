package com.github.w_kamil.shoppingapp.products;

import android.support.v7.widget.PopupMenu;

public interface OnSingleProductMenuItemClickListner extends PopupMenu.OnMenuItemClickListener {
    void setSearchedProductBarcode(String selectedProductBarcode);
}
