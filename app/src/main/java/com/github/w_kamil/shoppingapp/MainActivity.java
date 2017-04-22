package com.github.w_kamil.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //TODO consider fragment implementation for single product in mainactivity

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
        Toast.makeText(this, "lista sklepów", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.goto_products)
    void gotoProductsActivity() {
        Toast.makeText(this, "lista produktów", Toast.LENGTH_SHORT).show();
    }

}
