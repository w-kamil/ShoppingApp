package com.github.w_kamil.shoppingapp.singleProduct;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddShoppingFragment extends DialogFragment {

    public static final String PRODUCT_KEY = "product";
    @BindView(R.id.product_name)
    TextView productName;

    @BindView(R.id.product_barcode)
    TextView productBarcode;

    @BindView(R.id.shops_spinner)
    Spinner shopsSpinner;

    @BindView(R.id.price_edittext)
    EditText priceEditText;

    @BindView(R.id.date_picker)
    DatePicker datePicker;

    private ShoppingDatabaseDao dao;
    private Product product;


    public static AddShoppingFragment newInstance(Product product){
        AddShoppingFragment fragment = new AddShoppingFragment();
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_KEY, product);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        product = getArguments().getParcelable(PRODUCT_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_shopping, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        productName.setText(product.getDescription());
        productBarcode.setText(product.getBarCode());

        dao = new ShoppingDatabaseDao(getActivity());
        List<Shop> shopsToChooseFrom = dao.fetchAllShops();

        ArrayAdapter<Shop> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, shopsToChooseFrom);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shopsSpinner.setAdapter(adapter);

        builder.setTitle(R.string.add_shopping_entry)
                .setPositiveButton(getString(R.string.add), (dialog, which) -> {
                    Shop shop = (Shop) shopsSpinner.getSelectedItem();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, datePicker.getYear());
                    cal.set(Calendar.MONTH, datePicker.getMonth());
                    cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    Date choosenDate = cal.getTime();
                    BigDecimal price = new BigDecimal(priceEditText.getText().toString());
                    Shopping shoppingToAdd = new Shopping(product, shop, choosenDate, price);
                    dao.addShopping(shoppingToAdd);
                })
                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }
}
