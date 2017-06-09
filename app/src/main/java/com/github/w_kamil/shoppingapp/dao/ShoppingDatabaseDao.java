package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
            productsList.add(new Product(procuctId, barcode, description));
        }
        cursor.close();
        database.close();
        return productsList;
    }

    @Override
    public List<Product> fetchAllProductsMatchingSpecificShop(Shop shopToSearch) {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.MainTableEntry.TABLE, new String[]{ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_ID},
                ShoppingDatabaseContract.MainTableEntry.COL_SHOP_ID + " = ?", new String[]{String.valueOf(shopToSearch.getId())});
        List<Product> productsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            productsList.add(searchProduct(cursor.getInt(0)));
        }
        cursor.close();
        database.close();
        return null;
    }

    @Override
    public List<Shop> fetchAllShops() {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_SHOPS, null, null);
        List<Shop> shopsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry._ID);
            int shopId = cursor.getInt(indexId);
            int indexIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
            String shopIndentifier = cursor.getString(indexIdentifier);
            int indexAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(indexAddress);
            shopsList.add(new Shop(shopId, shopIndentifier, shopAddress));
        }
        cursor.close();
        database.close();
        return shopsList;
    }

    @Override
    public List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product productToSearch) {
        database = dbHelper.getReadableDatabase();
        cursor = new DbContentProvider().query(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_MAIN_TABLE,
                ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_ID + " = ?", new String[]{String.valueOf(productToSearch.getId())});
        List<Shopping> shoppingList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry._ID);
            int shoppingId = cursor.getInt(indexId);
            int indexProductId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_ID);
            Product product = searchProduct(cursor.getInt(indexProductId));
            int indexShopId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_ID);
            Shop shop = searchShop(cursor.getInt(indexShopId));
            int indesShoppingDate = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE);
            Date shoppingDate = new Date(cursor.getLong(indesShoppingDate));
            int indexPrice = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE);
            BigDecimal price = new BigDecimal(cursor.getString(indexPrice));
            shoppingList.add(new Shopping(shoppingId, product, shop, shoppingDate, price));
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
        new DbContentProvider().delete(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        int deletedRowsQty = new DbContentProvider().delete(ShoppingDatabaseContract.ProductsEntry.TABLE,
                ShoppingDatabaseContract.ProductsEntry._ID + " = ?", new String[]{String.valueOf(product.getId())});
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
    public int renameShop(Shop shop, String newShopName) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, newShopName);
        int updatedRows = new DbContentProvider().update(ShoppingDatabaseContract.ShopsEntry.TABLE,
                contentValues, ShoppingDatabaseContract.ShopsEntry._ID + " = ?", new String[]{String.valueOf(shop.getId())});
        return updatedRows;
    }

    @Override
    public int changeShopAddress(Shop shop, String newShopAddress) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS, newShopAddress);
        int updatedRows = new DbContentProvider().update(ShoppingDatabaseContract.ShopsEntry.TABLE,
                contentValues, ShoppingDatabaseContract.ShopsEntry._ID + " = ?", new String[]{String.valueOf(shop.getId())});
        return updatedRows;
    }

    @Override
    public int deleteShop(Shop shopToDeleteFromDb) {
        database = dbHelper.getWritableDatabase();
        new DbContentProvider().delete(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.MainTableEntry.COL_SHOP_ID + " = ?",
                new String[]{String.valueOf(shopToDeleteFromDb.getId())});
        int deletedRows = new DbContentProvider().delete(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.ShopsEntry._ID + " = ?",
                new String[]{String.valueOf(shopToDeleteFromDb.getId())});
        database.close();
        return deletedRows;
    }

    @Override
    public Product searchProduct(Integer productToSearchId) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, null,
                ShoppingDatabaseContract.ProductsEntry._ID + " = ?", new String[]{String.valueOf(productToSearchId)});
        if (cursor.moveToNext()) {
            int indexProductBarcode = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE);
            String productBarcode = cursor.getString(indexProductBarcode);
            int indexDescription = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION);
            String description = cursor.getString(indexDescription);
            Product product = new Product(productToSearchId, productBarcode, description);
            database.close();
            return product;
        } else {
            database.close();
            return null;
        }
    }

    @Override
    public Product searchProductByBarcode(String barcode) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, null,
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " = ?", new String[]{barcode});
        if (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry._ID);
            int id = cursor.getInt(indexId);
            int indexDescription = cursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION);
            String description = cursor.getString(indexDescription);
            Product product = new Product(id, barcode, description);
            database.close();
            return product;
        } else {
            database.close();
            return null;
        }
    }

    @Override
    public Shop searchShop(Integer shopToSearchId) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, null,
                ShoppingDatabaseContract.ShopsEntry._ID + " = ?", new String[]{String.valueOf(shopToSearchId)});
        if (cursor.moveToNext()) {
            int indexShopIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
            String shopIdentifier = cursor.getString(indexShopIdentifier);
            int indexShopAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(indexShopAddress);
            Shop shop = new Shop(shopToSearchId, shopIdentifier, shopAddress);
            database.close();
            return shop;
        } else {
            database.close();
            return null;
        }
    }


    @Override
    public long addShopping(Shopping singleShoppingItem) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE, singleShoppingItem.getDate().getTime());
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE, String.valueOf(singleShoppingItem.getPrice()));
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_ID, singleShoppingItem.getProduct().getId());
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_ID, singleShoppingItem.getShop().getId());
        long insertedRowID = new DbContentProvider().insert(ShoppingDatabaseContract.MainTableEntry.TABLE, contentValues);
        database.close();
        return insertedRowID;
    }

    @Override
    public int deleteShopping(Shopping singleShoppingItem) {
        database = dbHelper.getWritableDatabase();
        int deletedRowsQty = new DbContentProvider().delete(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.MainTableEntry._ID + " = ?",
                new String[]{String.valueOf(singleShoppingItem.getId())});
        database.close();
        return deletedRowsQty;
    }

    private class DbContentProvider {


        protected Cursor query(String tableName, String[] columnNames, String selection, String[] selectionArgs) {
            return database.query(tableName, columnNames, selection, selectionArgs, null, null, null);
        }

        protected long insert(String tableName, ContentValues values) {
            return database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_ROLLBACK);
        }

        protected int delete(String tableName, String selectionString, String[] selectionArgs) {
            return database.delete(tableName, selectionString, selectionArgs);
        }

        protected int update(String tableName, ContentValues values, String whereClause, String[] wherenArgs) {
            return database.update(tableName, values, whereClause, wherenArgs);
        }

    }
}
