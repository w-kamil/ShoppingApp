package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingDatabaseDao implements IShoppingDatabaseDao {


    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private Cursor cursor;

    public ShoppingDatabaseDao(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    @Override
    public List<Product> fetchAllProducts() {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_PRODUCTS, null, null);
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
        database.close();
        return productsList;
    }

    @Override
    public List<Product> fetchAllProductsMatchingSpecificShop(String shopIdentifier) {
        database = dbHelper.getReadableDatabase();
        String tablesNames = ShoppingDatabaseContract.ProductsEntry.TABLE + ", " + ShoppingDatabaseContract.MainTableEntry.TABLE;

        String[] colunmsNames = ShoppingDatabaseContract.COLUMNS_NAMES_PRODUCTS_LONG;
        String selection = ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER + " = ?";
        String[] selectionArgs = {shopIdentifier};
        cursor = new DbContentProvider().joinQuery(tablesNames, colunmsNames, selection, selectionArgs);
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
        database.close();
        return productsList;
    }

    @Override
    public List<Shop> fetchAllShops() {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_SHOPS, null, null);
        List<Shop> shopsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry._ID);
            String shopId = cursor.getString(indexId);
            int indexIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
            String shopIndentifier = cursor.getString(indexIdentifier);
            int inndexAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(inndexAddress);
            shopsList.add(new Shop(shopIndentifier, shopAddress));
        }
        cursor.close();
        database.close();
        return shopsList;
    }


    @Override
    public List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(String productBarcode) {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_MAIN_TABLE,
                ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE + " = ?", new String[]{productBarcode});
        List<Shopping> shoppingList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry._ID);
            String shoppingId = cursor.getString(indexId);
            int indexBarcode = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE);
            String barcode = cursor.getString(indexBarcode);
            Product product = searchProduct(barcode);
            int indexShopIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER);
            String shopIdentifier = cursor.getString(indexShopIdentifier);
            Shop shop = searchShop(shopIdentifier);
            int indexShopiingDate = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE);
            Date shoppingDate = new Date(cursor.getLong(indexShopiingDate));
            int indexPrice = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE);
            BigDecimal price = new BigDecimal(cursor.getString(indexPrice));
            Shopping shopping = new Shopping(shoppingId, product, shop, shoppingDate, price);
            shoppingList.add(shopping);
        }
        cursor.close();
        database.close();
        return shoppingList;
    }

    @Override
    public long addProduct(Product product) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, product.getBarCode());
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION, product.getDescription());
        long insertedRowId = new DbContentProvider().insert(ShoppingDatabaseContract.ProductsEntry.TABLE, contentValues);
        database.close();
        return insertedRowId;
    }

    @Override
    public int deleteProduct(Product product) {
        database = dbHelper.getWritableDatabase();
        int deletedRowsQty = new DbContentProvider().delete(ShoppingDatabaseContract.ProductsEntry.TABLE,
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " = ?", new String[]{product.getBarCode()});
        database.close();
        return deletedRowsQty;
    }

    @Override
    public long addShop(Shop shop) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, shop.getIdentifier());
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS, shop.getAddress());
        long insertedRowId = new DbContentProvider().insert(ShoppingDatabaseContract.ShopsEntry.TABLE, contentValues);
        database.close();
        return insertedRowId;
    }

    @Override
    public int deleteShop(String shopIdentifier) {
        database = dbHelper.getWritableDatabase();
        int deletedRowsQty = new DbContentProvider().delete(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER,
                new String[]{shopIdentifier});
        database.close();
        return deletedRowsQty;
    }

    @Override
    public Product searchProduct(String productBarcode) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, null,
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " = ?", new String[]{productBarcode});
        if (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry._ID);
            int procuctId = cursor.getInt(indexId);
            int indexDescription = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION);
            String description = cursor.getString(indexDescription);
            Product product = new Product(productBarcode, description);
            return product;
        } else {
            return null;
        }

    }

    @Override
    public Shop searchShop(String shopIdentifier) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, null,
                ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + " = ?", new String[]{shopIdentifier});
        if (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry._ID);
            int procuctId = cursor.getInt(indexId);
            int indexShopAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(indexShopAddress);
            Shop shop = new Shop(shopIdentifier, shopAddress);
            return shop;
        } else {
            return null;
        }
    }

    @Override
    public long addShopping(Shopping singleShoppingItem) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE, singleShoppingItem.getDate().getTime());
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE, String.valueOf(singleShoppingItem.getPrice()));
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE, singleShoppingItem.getProduct().getBarCode());
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER, singleShoppingItem.getShop().getIdentifier());
        long insertedRowID = new DbContentProvider().insert(ShoppingDatabaseContract.MainTableEntry.TABLE, contentValues);
        database.close();
        return insertedRowID;
    }

    @Override
    public int deleteShopping(Shopping singleShoppingItem) {
        database = dbHelper.getWritableDatabase();
        int deletedRowsQty = new DbContentProvider().delete(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.MainTableEntry._ID,
                new String[]{singleShoppingItem.getId()});
        database.close();
        return deletedRowsQty;
    }

    private class DbContentProvider {


        protected Cursor query(String tableName, String[] columnNames, String selection, String[] selectionArgs) {
            return database.query(tableName, columnNames, selection, selectionArgs, null, null, null);
        }

        protected Cursor joinQuery(String tablesNames, String[] columnNames, String selection, String[] selectionArgs) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(tablesNames);
            return queryBuilder.query(database, columnNames, selection, selectionArgs, null, null, null);
        }

        protected long insert(String tableName, ContentValues values) {
            database = dbHelper.getWritableDatabase();
            return database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
        }

        protected int delete(String tableName, String selectionString, String[] selectionArgs) {
            database = dbHelper.getWritableDatabase();
            return database.delete(tableName, selectionString, selectionArgs);
        }

    }
}
