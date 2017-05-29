package com.github.w_kamil.shoppingapp.singleProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.Shop;
import com.github.w_kamil.shoppingapp.dao.Shopping;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;
import com.github.w_kamil.shoppingapp.shops.ShopsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleProductActivity extends AppCompatActivity {

    @BindView(R.id.product_data)
    TextView productDataTextView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static final String PRODUCT_BARCODE = "product_barcode";
    private ShoppingDatabaseDao dao;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        ButterKnife.bind(this);
        String productBarcode = getIntent().getExtras().getString(PRODUCT_BARCODE);
        dao = new ShoppingDatabaseDao(this);
        product = dao.searchProduct(productBarcode);
        productDataTextView.setText(product.getDescription() + "\n" + String.format(getString(R.string.barcode_value), productBarcode));
    }

    @OnClick(R.id.add_new_shopping)
    void addNewShoppingEntry() {
        List<Shop> shopsToChooseFrom = dao.fetchAllShops();


        AlertDialog addShoppingDialog = new AlertDialog.Builder(this).setTitle(R.string.add_shopping_entry)
                .setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Shopping shoppingToAdd = new Shopping(product, );
                        dao.addShopping(shoppingToAdd);
//                        dao.addShopping(new Shopping("1", "59045", "abc", new Date(), new BigDecimal(10)));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    public static Intent createIntent (String productBarcode, Context context){
        Intent intent = new Intent(context,SingleProductActivity.class);
        intent.putExtra(PRODUCT_BARCODE, productBarcode);
        return intent;
    }
}
