package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class ShoppingDatabaseDao implements IShoppingDatabaseDao {

    //TODO implement dao methods
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase readabledatabase;
    SQLiteDatabase writeabledatabase;

    protected ShoppingDatabaseDao(Context context) {
        this.dbHelper = new DbHelper(context);
        writeabledatabase = dbHelper.getWritableDatabase();
        readabledatabase = dbHelper.getReadableDatabase();
    }

    @Override
    public List<Product> fetchAllProducts() {
        return null;
    }

    @Override
    public List<Product> fetchAllProductsMatchingSpecificShop(Shop shop) {
        return null;
    }

    @Override
    public List<Shop> fetchAllShops() {
        return null;
    }

    @Override
    public List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product product) {
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
    public boolean addShopping(Shopping singleShoppingItem) {
        return false;
    }

    @Override
    public boolean deleteShopping(Shopping singleShoppingItem) {
        return false;
    }


    private class DbContentProvider {

        protected Cursor query(String tableName, String[] columnNames) {
            return readabledatabase.query(
                    tableName,
                    columnNames,
                    null, null, null, null, null);

        }

        protected boolean insert(String tableName, ContentValues values) {


            return (writeabledatabase.insertWithOnConflict(
                    tableName,
                    null, values, SQLiteDatabase.CONFLICT_REPLACE) > -1);
        }

        protected boolean delete(String tableName, String selectionString, String[] selectionArgs) {
            return (writeabledatabase.delete(
                    tableName,
                    selectionString,
                    selectionArgs) > 0);
        }

    }


}
