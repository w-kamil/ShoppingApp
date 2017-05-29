package com.github.w_kamil.shoppingapp.singleProduct;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.util.List;


public class AddShoppingFragment extends DialogFragment {

    private ShoppingDatabaseDao dao;
    private Product product;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dao = new ShoppingDatabaseDao(getActivity());
        List<Shop> shopsToChooseFrom = dao.fetchAllShops();
        builder.setTitle(R.string.add_shopping_entry)
                .setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Shopping shoppingToAdd = new Shopping(product, );
                        dao.addShopping(shoppingToAdd);
//                        dao.addShopping(new Shopping("1", "59045", "abc", new Date(), new BigDecimal(10)));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
        return builder.create();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
