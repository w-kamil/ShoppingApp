package com.github.w_kamil.shoppingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.dao.DbHelper;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseContract;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.products.ProductsActivity;
import com.github.w_kamil.shoppingapp.shops.ShopsActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //TODO implement AsynsTask class


    private String scanResult;
    private static final String SCAN_RESULT_KEY = "scanResultKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            scanResult = savedInstanceState.getString(SCAN_RESULT_KEY);
        }


        //TODO delete these lines - used only for test, handle multiplied unique values exception
        ShoppingDatabaseDao dao = new ShoppingDatabaseDao(this);
//        dao.addProduct(new Product("59045", "ser"));
//        dao.addProduct(new Product("59045", "ser"));
//        dao.addProduct(new Product("59046", "ser"));
//        dao.fetchAllProducts();
//        dao.deleteProduct(new Product("59046", "ser"));
//        dao.deleteProduct(new Product("59045", "ser"));

//        dao.addShop(new Shop("abc", "warszawa"));
//        dao.addShop(new Shop("abc", "warszawa"));
//        dao.addShop(new Shop("abcD", "warszawa"));
//        dao.fetchAllShops();
//        dao.deleteShop("abc");

//        dao.addShopping(new Shopping("1", "59045", "abc", new Date(), new BigDecimal(10)));
//        dao.addShopping(new Shopping("1", "59045", "abc", new Date(), new BigDecimal(10)));
        dao.fetchAllProductsMatchingSpecificShop("abc");
        dao.fetchAllShoppingItemsMatchingSpecificProduct("59046");


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SCAN_RESULT_KEY, scanResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, R.string.scan_cancelled, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wynik skanu: " + intentResult.getContents(), Toast.LENGTH_LONG).show();
                scanResult = intentResult.getContents();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.goto_scanner)
    void startScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        integrator.setPrompt(getString(R.string.scan_product));
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @OnClick(R.id.goto_shops)
    void startShopsActivity() {
        Intent intent = new Intent(this, ShopsActivity.class);
        startActivity(intent);
        Toast.makeText(this, "lista sklepów", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.goto_products)
    void gotoProductsActivity() {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
        Toast.makeText(this, "lista produktów", Toast.LENGTH_SHORT).show();
    }
}
