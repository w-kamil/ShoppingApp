package com.github.w_kamil.shoppingapp.dao;


import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ShoppingDatabaseDao extends DbContentProvider implements IShoppingDatabaseDao {


    //TODO implement methods

    protected ShoppingDatabaseDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<Product> fetchAllProducts() {
        return null;
    }

    @Override
    public List<Product> fetchAllProductsMachingSpecificShopIdentifier(String indentifier) {
        return null;
    }

    @Override
    public List<Shop> fetchsAllShops() {
        return null;
    }

    @Override
    public List<Shopping> fetchShoppingMatchingSpecfingBarcode(String barcode) {
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(Product product) {
        return false;
    }

    @Override
    public boolean addShop(Shop shop) {
        return false;
    }

    @Override
    public boolean deleteShop(Shop shop) {
        return false;
    }

    @Override
    public boolean addShopping(Shopping shopping) {
        return false;
    }

    //TOFO implement data access methods, what provide access from Main activity(DbHelper and Database objects)

}
