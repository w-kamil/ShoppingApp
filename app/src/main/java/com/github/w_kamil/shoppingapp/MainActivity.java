package com.github.w_kamil.shoppingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.goto_scanner_textview) void startScanner(){
        Toast.makeText(this, "skaner", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.goto_shops_textview) void startShopsActivity(){
        Toast.makeText(this, "lista sklepów", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.goto_products_textview) void gotoProductsActivity(){
        Toast.makeText(this, "lista produktów", Toast.LENGTH_SHORT).show();
    }

}
