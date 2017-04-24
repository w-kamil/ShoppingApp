package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDatabaseDao implements IShoppingDatabaseDao {

    //TODO implement dao methods
    SQLiteOpenHelper dbHelper;

    public ShoppingDatabaseDao(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    @Override
    public List<Product> fetchAllProducts() {
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_PRODUCTS, null, null);
        List<Product> productsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry._ID);
            int procuctId = cursor.getInt(indexId);
            int indexBarcode = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE);
            String barcode = cursor.getString(indexBarcode);
            int indexDescription = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION);
            String description = cursor.getString(indexDescription);
            productsList.add(new Product(barcode, description));
        }
        cursor.close();
        return productsList;
    }

    @Override
    public List<Product> fetchAllProductsMatchingSpecificShop(Shop shop) {
        String tablesNames = ShoppingDatabaseContract.ProductsEntry.TABLE + ", " + ShoppingDatabaseContract.MainTableEntry.TABLE;
//        optional tables statemtent to be checked
//        String tablesNamesOptional = ShoppingDatabaseContract.ProductsEntry.TABLE + " JOIN " + ShoppingDatabaseContract.MainTableEntry.TABLE
//                + " ON (" + ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry._ID + " = "
//                + ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.PRODUTCS_ID + " );" ;

        String[] colunmsNames = {ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE,
        ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION};
        String selection = ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.SHOPS_ID;
        String[] selectionArgs = {String.valueOf(shop.getId())};
        Cursor cursor = new DbContentProvider().joinQuery(tablesNames, colunmsNames, selection, selectionArgs);
        List<Product> productsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry._ID);
            int procuctId = cursor.getInt(indexId);
            int indexBarcode = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE);
            String barcode = cursor.getString(indexBarcode);
            int indexDescription = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION);
            String description = cursor.getString(indexDescription);
            productsList.add(new Product(barcode, description));
        }
        cursor.close();
        return productsList;
    }

    @Override
    public List<Shop> fetchAllShops() {
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_SHOPS, null, null);
        List<Shop> shopsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry._ID);
            int shopId = cursor.getInt(indexId);
            int indexIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
            String shopIndentifier = cursor.getString(indexIdentifier);
            int inndexAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(inndexAddress);
            shopsList.add(new Shop(shopId, shopIndentifier, shopAddress));
        }
        return shopsList;
    }

    @Override
    public List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product product) {
        return null;
    }

    @Override
    public long addProduct(Product product) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, product.getBarCode());
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION, product.getDescription());
        return new DbContentProvider().insert(ShoppingDatabaseContract.ProductsEntry.TABLE, contentValues);
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

        SQLiteDatabase database;

        protected Cursor query(String tableName, String[] columnNames, String selection, String[] selectionArgs) {
            database = dbHelper.getReadableDatabase();
            return database.query(tableName, columnNames, selection, selectionArgs, null, null, null);
        }

        protected Cursor joinQuery(String tablesNames, String[] columnNames, String selection, String[] selectionArgs) {
            database = dbHelper.getReadableDatabase();
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(tablesNames);
            return queryBuilder.query(database, columnNames, selection, selectionArgs, null, null, null);
        }

        protected long insert(String tableName, ContentValues values) {
            database = dbHelper.getWritableDatabase();
            return database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
        }

        protected boolean delete(String tableName, String selectionString, String[] selectionArgs) {
            database = dbHelper.getWritableDatabase();
            return (database.delete(tableName, selectionString, selectionArgs) > 0);
        }

    }
}
