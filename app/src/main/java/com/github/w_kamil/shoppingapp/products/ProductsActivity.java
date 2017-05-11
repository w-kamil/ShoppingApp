package com.github.w_kamil.shoppingapp.products;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //TODO implement presnter for providing data to ProductsActivity
        ShoppingDatabaseDao dao = new ShoppingDatabaseDao(this);
        List<Product> products = dao.fetchAllProducts();

        ProductsListAdapter adapter = new ProductsListAdapter(products);
        adapter.setOnMenuItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //TODO implement menu actions (intent to scanner and list settings/filtering)
        switch (item.getItemId()) {
            case R.id.scan_product:
                return true;
            case (R.id.settings):
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //TODO implement popup menu options and move shopping history to onitemclicklistener
        switch (item.getItemId()) {
            case R.id.show_shopping_history:
                Toast.makeText(this, "Na ten przycisk przejdziesz do historii zakupów produktu", Toast.LENGTH_SHORT).show();
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
}
