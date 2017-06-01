package com.github.w_kamil.shoppingapp.products;

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
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.singleProduct.SingleProductActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, OnSingleProductMenuItemClickListner {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String scanResult;
    private String searchedProductBarcode;
    private IShoppingDatabaseDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //TODO implement presnter for providing data to ProductsActivity
        dao = new ShoppingDatabaseDao(this);
        List<Product> products = dao.fetchAllProducts();

        ProductsListAdapter adapter = new ProductsListAdapter(products);
        adapter.setOnSingleProductMenuItemClickListner(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //TODO implement list settings/filtering
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
        //TODO implement popup menu options and move shopping history (single product activity) to onitemclicklistener
        switch (item.getItemId()) {
            case R.id.show_shopping_history:
                gotoSingleProductActivity(searchedProductBarcode);
                break;
            case R.id.change_product_description:
                Toast.makeText(this, "Tu będziesz mógł zmienić opis produktu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_product:
                Toast.makeText(this, "Tym przeyciskiem będziesz mógł usunąc dany produkt z listy", Toast.LENGTH_SHORT).show();
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
                if (dao.searchProduct(scanResult) != null) {
                    gotoSingleProductActivity(scanResult);
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
                                gotoSingleProductActivity(scanResult);
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

    private void gotoSingleProductActivity(String productBarcode) {
        startActivity(SingleProductActivity.createIntent(productBarcode, this));
    }


    @Override
    public void setSearchedProductBarcode(String selectedProductBarcode) {
        this.searchedProductBarcode = selectedProductBarcode;
    }
}
