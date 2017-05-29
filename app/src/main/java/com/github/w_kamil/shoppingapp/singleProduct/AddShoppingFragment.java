package com.github.w_kamil.shoppingapp.singleProduct;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddShoppingFragment extends DialogFragment {


    @BindView(R.id.shops_spinner)
    Spinner shopsSpinner;


    @BindView(R.id.price_edittext)
    EditText priceEditText;


    private ShoppingDatabaseDao dao;
    private Product product;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_shopping, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        dao = new ShoppingDatabaseDao(getActivity());
        List<Shop> shopsToChooseFrom = dao.fetchAllShops();

        ArrayAdapter<Shop> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, shopsToChooseFrom);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shopsSpinner.setAdapter(adapter);




        builder.setTitle(R.string.add_shopping_entry)
                .setPositiveButton(getString(R.string.add), (dialog, which) -> {

                    Shop shop = (Shop) shopsSpinner.getSelectedItem();
                    BigDecimal price = new BigDecimal(priceEditText.getText().toString());
                    Shopping shoppingToAdd = new Shopping(product,shop, ,price);
                    dao.addShopping(shoppingToAdd);
//                        dao.addShopping(new Shopping("1", "59045", "abc", new Date(), new BigDecimal(10)));
                })
                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
