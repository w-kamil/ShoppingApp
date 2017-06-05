package com.github.w_kamil.shoppingapp.singleProduct;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleProductActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, OnSingleShoppingMenuItemClickListener, ShoppingListUpdater {

    @BindView(R.id.product_data)
    TextView productDataTextView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static final String PRODUCT_BARCODE = "product_barcode";
    private ShoppingDatabaseDao dao;
    private Product product;
    private Shopping selectedShoppingEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        ButterKnife.bind(this);
        String productBarcode = getIntent().getExtras().getString(PRODUCT_BARCODE);
        dao = new ShoppingDatabaseDao(this);
        product = dao.searchProduct(productBarcode);
        productDataTextView.setText(product.getDescription() + "\n" + String.format(getString(R.string.barcode_value), productBarcode));
        updateUI();
    }

    @OnClick(R.id.add_new_shopping)
    void addNewShoppingEntry() {
        DialogFragment addShoppingFragment = AddShoppingFragment.newInstance(product, this);
        addShoppingFragment.show(getSupportFragmentManager(), "dialog");

    }

    public static Intent createIntent(Context context, String productBarcode) {
        Intent intent = new Intent(context, SingleProductActivity.class);
        intent.putExtra(PRODUCT_BARCODE, productBarcode);
        return intent;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_shopinng:
                dao.deleteShopping(selectedShoppingEntry);
                updateUI();
                break;
        }
        return false;
    }

    @Override
    public void setShoppingEntry(Shopping selectedShoppingEntry) {
        this.selectedShoppingEntry = selectedShoppingEntry;
    }

    public void updateUI() {
        List<Shopping> shoppingList = dao.fetchAllShoppingItemsMatchingSpecificProduct(product.getBarCode());
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(shoppingList, this);
        shoppingListAdapter.setOnSingleShoppingMenuItemClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(shoppingListAdapter);
    }

}
