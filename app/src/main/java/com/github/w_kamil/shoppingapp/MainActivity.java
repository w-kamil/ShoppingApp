package com.github.w_kamil.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.products.ProductsActivity;
import com.github.w_kamil.shoppingapp.shops.ShopsActivity;
import com.github.w_kamil.shoppingapp.singleProduct.SingleProductActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String scanResult;
    private static final String SCAN_RESULT_KEY = "scanResultKey";
    private ShoppingDatabaseDao dao;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dao = new ShoppingDatabaseDao(this);
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
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogLayout = layoutInflater.inflate(R.layout.dialog_add_product, null);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, R.string.scan_cancelled, Toast.LENGTH_SHORT).show();
            } else {
                scanResult = intentResult.getContents();
                product = dao.searchProductByBarcode(scanResult);
                if (product != null) {
                    gotoSingleProductActivity(product);
                } else {
                    AlertDialog createNewProductDialog = new AlertDialog.Builder(this)
                            .setTitle(String.format(getString(R.string.product_is_not_in_database), scanResult))
                            .setMessage(R.string.dialog_add_product)
                            .setView(dialogLayout)
                            .setPositiveButton(R.string.add_product, null)
                            .setNegativeButton(R.string.cancel, null)
                            .create();

                    createNewProductDialog.setOnShowListener(dialog -> {
                        Button positiveButton = createNewProductDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        final EditText descriptopnInputEditText = (EditText) dialogLayout.findViewById(R.id.description_input);
                        positiveButton.setOnClickListener(v -> {
                            if (descriptopnInputEditText.getText().length() == 0) {
                                Toast.makeText(this, getResources().getString(R.string.enter_product_description), Toast.LENGTH_SHORT).show();
                            } else {
                                Product productToAdd = new Product(scanResult, descriptopnInputEditText.getText().toString());
                                dao.addProduct(productToAdd);
                                gotoSingleProductActivity(product);
                                createNewProductDialog.dismiss();
                            }
                        });
                    });
                    createNewProductDialog.show();
                }
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
    }

    @OnClick(R.id.goto_products)
    void gotoProductsActivity() {
        startActivity(ProductsActivity.createIntent(this, null));
    }

    private void gotoSingleProductActivity(Product product) {
        startActivity(SingleProductActivity.createIntent(this, product));
    }
}
