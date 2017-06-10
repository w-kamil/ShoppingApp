package com.github.w_kamil.shoppingapp.shops;

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
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.products.ProductsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, OnSingleShopMenuItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ShoppingDatabaseDao dao;
    private Shop selectedShopEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));

        dao = new ShoppingDatabaseDao(this);
        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shops_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_shop:
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.dialog_add_shop, null);
                AlertDialog addNewShopDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.adding_new_shop)
                        .setMessage(R.string.dialog_add_shop)
                        .setView(dialogLayout)
                        .setPositiveButton(R.string.add_new_shop, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create();
                addNewShopDialog.setOnShowListener(dialog -> {
                    Button positiveButton = addNewShopDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText shopNameEditText = (EditText) dialogLayout.findViewById(R.id.name_edit_text);
                    final EditText shopAddressEditText = (EditText) dialogLayout.findViewById(R.id.address_edit_text);
                    positiveButton.setOnClickListener(v -> {
                        if (shopNameEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_shop_name), Toast.LENGTH_SHORT).show();
                        } else if (shopAddressEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_shop_address), Toast.LENGTH_SHORT).show();
                        } else {
                            Shop shopToAdd = new Shop(shopNameEditText.getText().toString(), shopAddressEditText.getText().toString());
                            if (dao.searchShop(shopToAdd.getId()) != null) {
                                Toast.makeText(this, shopToAdd.getIdentifier() + " " + getString(R.string.shop_name_already_exists), Toast.LENGTH_LONG).show();
                            } else {
                                dao.addShop(shopToAdd);
                                updateUI();
                                addNewShopDialog.dismiss();
                            }
                        }
                    });
                });
                addNewShopDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_products_in_shop:
                gotoProductstActivity(selectedShopEntry);
                break;
            case R.id.rename_shop_identifier:
                AlertDialog renameShopDialog = new AlertDialog.Builder(this).
                        setTitle(R.string.enter_new_shop_name).
                        setView(inflateUpdatingDialogLayout()).
                        setPositiveButton(R.string.confirm, null).
                        setNegativeButton(R.string.cancel, null).
                        create();
                renameShopDialog.setOnShowListener(dialog -> {
                    Button positiveButton = renameShopDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText shopNameEditText = (EditText) renameShopDialog.findViewById(R.id.data_edit_text);
                    positiveButton.setOnClickListener(v -> {
                        if (shopNameEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_shop_name), Toast.LENGTH_SHORT).show();
                        } else {
                            dao.renameShop(selectedShopEntry, shopNameEditText.getText().toString());
                            updateUI();
                            renameShopDialog.dismiss();
                        }
                    });
                });
                renameShopDialog.show();
                break;
            case R.id.change_shop_address:
                AlertDialog changeShopAddressDialog = new AlertDialog.Builder(this).
                        setTitle(R.string.exner_new_shop_address).
                        setView(inflateUpdatingDialogLayout()).
                        setPositiveButton(R.string.confirm, null).
                        setNegativeButton(R.string.cancel, null).
                        create();
                changeShopAddressDialog.setOnShowListener(dialog -> {
                    Button positiveButton = changeShopAddressDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    final EditText shopAddressEditText = (EditText) changeShopAddressDialog.findViewById(R.id.data_edit_text);
                    positiveButton.setOnClickListener(v -> {
                        if (shopAddressEditText.getText().length() == 0) {
                            Toast.makeText(this, getResources().getString(R.string.enter_shop_address), Toast.LENGTH_SHORT).show();
                        } else {
                            dao.changeShopAddress(selectedShopEntry, shopAddressEditText.getText().toString());
                            updateUI();
                            changeShopAddressDialog.dismiss();
                        }
                    });
                });
                changeShopAddressDialog.show();
                break;
            case R.id.delete_shop:

                dao.deleteShop(selectedShopEntry);
                updateUI();
                break;
        }

        return false;
    }

    private View inflateUpdatingDialogLayout() {
        LayoutInflater inflater = getLayoutInflater();
        return inflater.inflate(R.layout.dialog_update_shop, null);
    }


    public void setSelectedShopEntry(Shop selectedShopEntry) {
        this.selectedShopEntry = selectedShopEntry;
    }

    private void gotoProductstActivity(Shop singleShopToShow) {
        startActivity(ProductsActivity.createIntent(this, singleShopToShow));
    }

    private void updateUI() {
        List<Shop> shops = dao.fetchAllShops();
        ShopsListAdapter adapter = new ShopsListAdapter(shops);
        adapter.setOnSingleShopMenuItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}


