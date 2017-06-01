package com.github.w_kamil.shoppingapp.products;

import android.support.v7.widget.PopupMenu;

/**
 * Created by Frod_ on 01.06.2017.
 */

public interface OnSingleProductMenuItemClickListner extends PopupMenu.OnMenuItemClickListener {
    void setSearchedProductBarcode(String selectedProductBarcode);
}
