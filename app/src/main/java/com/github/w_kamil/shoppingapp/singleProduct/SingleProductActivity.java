package com.github.w_kamil.shoppingapp.singleProduct;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.w_kamil.shoppingapp.R;
import com.github.w_kamil.shoppingapp.dao.Product;
import com.github.w_kamil.shoppingapp.dao.ShoppingDatabaseDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleProductActivity extends AppCompatActivity {

    @BindView(R.id.product_data)
    TextView productDataTextView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static final String PRODUCT_BARCODE = "product_barcode";
    private ShoppingDatabaseDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        ButterKnife.bind(this);
        String productBarcode = getIntent().getExtras().getString(PRODUCT_BARCODE);
        dao = new ShoppingDatabaseDao(this);
        Product product = dao.searchProduct(productBarcode);
        productDataTextView.setText(product.getDescription() + "\nkod produktu: " + productBarcode);
    }

    public static Intent createIntent (String productBarcode, Context context){
        Intent intent = new Intent(context,SingleProductActivity.class);
        intent.putExtra(PRODUCT_BARCODE, productBarcode);
        return intent;
    }
}
