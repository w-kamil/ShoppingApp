package com.github.w_kamil.shoppingapp.shops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));


        //TODO implement presnter for providing data to ShopsActivity
        ShoppingDatabaseDao dao = new ShoppingDatabaseDao(this);
        List<Shop> shops = dao.fetchAllShops();
        ShopsListAdapter adapter = new ShopsListAdapter(shops);
        adapter.setOnMenuItemClickListener(this);
        recyclerView.setAdapter(adapter);
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
                //TODO implement adding new shop to list popup window
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //TODO implement popup menu options
        switch (item.getItemId()) {
            case R.id.show_products_in_shop:
                Toast.makeText(this, "Na ten przycisk przejdziesz do list produktow", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rename_shop_identifier:
                Toast.makeText(this, "Tu będziesz mógł zmienić nazwę sklepu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_shop_address:
                Toast.makeText(this, "Tu będziesz mógł zmienić adres sklepu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_shop:
                Toast.makeText(this, "Tym przeyciskiem będziesz mógł usunąc dany sklep", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }
}


