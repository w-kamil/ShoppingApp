package com.github.w_kamil.shoppingapp.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.IShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.singleProduct.SingleProductActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, OnSingleProductMenuItemClickListner {

    public static final String SINGLE_SHOP_TO_SHOW_KEY = "single Shop To Show Key";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String scanResult;
    private Product searchedProduct;
    private Shop singleShopToShow;
    private IShoppingDatabaseDao dao;
    private List<Product> products;


    public static Intent createIntent(Context context, Shop singleShopToShow) {
        Intent intent = new Intent(context, ProductsActivity.class);
        intent.putExtra(SINGLE_SHOP_TO_SHOW_KEY, singleShopToShow);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        products = Collections.emptyList();
        singleShopToShow = getIntent().getExtras().getParcelable(SINGLE_SHOP_TO_SHOW_KEY);
        dao = new ShoppingDatabaseDao(this);
        updateUI();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.scan_product:
                startScanner();
                return true;
            case (R.id.settings):
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_shopping_history:
                gotoSingleProductActivity(searchedProduct);
                break;
            case R.id.change_product_description:
                AlertDialog changeProductDescriptionDialog = new AlertDialog.Builder(this).
                        setTitle(R.string.enter_new_product_description).
                        setView(getLayoutInflater().inflate(R.layout.dialog_update_shop, null)).
                        setPositiveButton(R.string.confirm, null).
                        setNegativeButton(R.string.cancel, null).
                        create();
                changeProductDescriptionDialog.setOnShowListener(dialog -> {
                    Button positiveButton = changeProductDescriptionDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText productDescriptionEditText = (EditText) changeProductDescriptionDialog.findViewById(R.id.data_edit_text);
                    positiveButton.setOnClickListener(v -> {
                        if (productDescriptionEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_shop_name), Toast.LENGTH_SHORT).show();
                        } else {
                            dao.changeProductDescription(searchedProduct, productDescriptionEditText.getText().toString());
                            updateUI();
                            changeProductDescriptionDialog.dismiss();
                        }
                    });
                });
                changeProductDescriptionDialog.show();
                break;
            case R.id.delete_product:
                dao.deleteProduct(searchedProduct);
                updateUI();
                break;
        }
        return false;
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
                Product scannedProduct = dao.searchProductByBarcode(scanResult);
                if (scannedProduct != null) {
                    gotoSingleProductActivity(scannedProduct);
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
                                gotoSingleProductActivity(productToAdd);
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

    private void startScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        integrator.setPrompt(getString(R.string.scan_product));
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private void gotoSingleProductActivity(Product product) {
        startActivity(SingleProductActivity.createIntent(this, product));
    }


    @Override
    public void setSearchedProduct(Product product) {
        this.searchedProduct = product;
    }

    private void updateUI() {
        if (singleShopToShow != null) {
            products = dao.fetchAllProductsMatchingSpecificShop(singleShopToShow);
        } else {
            products = dao.fetchAllProducts();
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ProductsListAdapter adapter = new ProductsListAdapter(products);
        adapter.setOnSingleProductMenuItemClickListner(this);
        recyclerView.setAdapter(adapter);
    }
}
