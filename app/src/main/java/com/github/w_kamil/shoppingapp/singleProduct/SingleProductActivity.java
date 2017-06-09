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

    public static final String PRODUCT_KEY = "product_key";
    private ShoppingDatabaseDao dao;
    private Product product;
    private Shopping selectedShoppingEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        ButterKnife.bind(this);
        product = getIntent().getExtras().getParcelable(PRODUCT_KEY);
        dao = new ShoppingDatabaseDao(this);
        productDataTextView.setText(product.getDescription() + "\n" + String.format(getString(R.string.barcode_value), product.getBarCode()));
        updateUI();
    }

    @OnClick(R.id.add_new_shopping)
    void addNewShoppingEntry() {
        DialogFragment addShoppingFragment = AddShoppingFragment.newInstance(product, this);
        addShoppingFragment.show(getSupportFragmentManager(), "dialog");

    }

    public static Intent createIntent(Context context, Product product) {
        Intent intent = new Intent(context, SingleProductActivity.class);
        intent.putExtra(PRODUCT_KEY, product);
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
        List<Shopping> shoppingList = dao.fetchAllShoppingItemsMatchingSpecificProduct(product);
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(shoppingList, this);
        shoppingListAdapter.setOnSingleShoppingMenuItemClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(shoppingListAdapter);
    }

}
