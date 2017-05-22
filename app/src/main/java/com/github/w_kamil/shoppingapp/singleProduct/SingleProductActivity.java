package com.github.w_kamil.shoppingapp.singleProduct;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.w_kamil.shoppingapp.R;

public class SingleProductActivity extends AppCompatActivity {

    public static final String PRODUCT_BARCODE = "product_barcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
    }

    public static Intent createIntent (String productBarcode, Context context){
        Intent intent = new Intent(context,SingleProductActivity.class);
        intent.putExtra(PRODUCT_BARCODE, productBarcode);
        return intent;
    }
}
