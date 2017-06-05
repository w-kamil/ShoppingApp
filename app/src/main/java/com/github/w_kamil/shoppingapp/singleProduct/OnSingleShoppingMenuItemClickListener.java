package com.github.w_kamil.shoppingapp.singleProduct;


import android.support.v7.widget.PopupMenu;
import com.github.w_kamil.shoppingapp.dao.Shopping;

public interface OnSingleShoppingMenuItemClickListener extends PopupMenu.OnMenuItemClickListener {
    void setShoppingEntry(Shopping selectedShoppingEntry);
}
