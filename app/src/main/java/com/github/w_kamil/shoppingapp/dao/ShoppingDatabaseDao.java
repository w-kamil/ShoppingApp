package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingDatabaseDao implements IShoppingDatabaseDao {

    //TODO check dao methods
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
//                + ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTC_BARCODE + " );" ;

        String[] colunmsNames = {ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE,
                ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION};
        String selection = ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER + " = ?";
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
            String shopId = cursor.getString(indexId);
            int indexIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
            String shopIndentifier = cursor.getString(indexIdentifier);
            int inndexAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
            String shopAddress = cursor.getString(inndexAddress);
            shopsList.add(new Shop(shopId, shopIndentifier, shopAddress));
        }
        cursor.close();
        return shopsList;
    }

    @Override
    public List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product product) {
        String tablesNames = ShoppingDatabaseContract.MainTableEntry.TABLE + ", " + ShoppingDatabaseContract.ProductsEntry.TABLE;
//        optional tables statemtent to be checked
//        String tablesNamesOptional = ShoppingDatabaseContract.MainTableEntry.TABLE + " JOIN " + ShoppingDatabaseContract.ShopsEntry.TABLE
//                + " ON (" + ShoppingDatabaseContract.ProductsEntry.TABLE + "." + ShoppingDatabaseContract.ProductsEntry._ID + " = "
//                + ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTC_BARCODE + ");";

        String[] colunmsNames = {ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE,
                ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE,
                ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTC_BARCODE,
                ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER};


        String selection = ShoppingDatabaseContract.MainTableEntry.TABLE + "." + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTC_BARCODE + " = ?";
        Cursor productCursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, new String[]{ShoppingDatabaseContract.ProductsEntry._ID},
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " = ?", new String[]{product.getBarCode()});
        String[] selectionArgs = new String[1];
        if (productCursor.moveToFirst()) {
            String searchProductId = productCursor.getString(0);
            selectionArgs[0] = searchProductId;
        }
        Cursor cursor = new DbContentProvider().joinQuery(tablesNames, colunmsNames, selection, selectionArgs);
        List<Shopping> shoppingList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Shop shop;
            int indexId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry._ID);
            int procuctId = cursor.getInt(indexId);
            int indexDate = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE);
            String date = cursor.getString(indexDate);
            int indexShopId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER);
            String shopId = cursor.getString(indexShopId);
            int indexPrice = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE);
            BigDecimal price = new BigDecimal(cursor.getString(indexPrice));
            Cursor shopCursor = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_SHOPS,
                    ShoppingDatabaseContract.ShopsEntry._ID + " = ?", new String[]{shopId});
            if (shopCursor.moveToFirst()) {
                int indexShoppingId = cursor.getColumnIndex(ShoppingDatabaseContract.MainTableEntry._ID);
                String shoppingId = cursor.getString(indexShoppingId);
                int indexShopIdentifier = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER);
                String identifier = cursor.getString(indexShopIdentifier);
                int indexAddress = cursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS);
                String address = cursor.getString(indexAddress);
                shop = new Shop(shopId, identifier, address);
                String shopIdentifier = shop.getIdentifier();
                shoppingList.add(new Shopping(shoppingId, product.getBarCode(), shopIdentifier, date, price));
            }

        }
        cursor.close();
        return shoppingList;
    }

    @Override
    public long addProduct(Product product) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, product.getBarCode());
        contentValues.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION, product.getDescription());
        return new DbContentProvider().insert(ShoppingDatabaseContract.ProductsEntry.TABLE, contentValues);
    }

    @Override
    public int deleteProduct(Product product) {
        return new DbContentProvider().delete(ShoppingDatabaseContract.ProductsEntry.TABLE,
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, new String[]{product.getBarCode()});
    }

    @Override
    public long addShop(Shop shop) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, shop.getIdentifier());
        contentValues.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS, shop.getAddress());
        return new DbContentProvider().insert(ShoppingDatabaseContract.ShopsEntry.TABLE, contentValues);
    }

    @Override
    public int deleteShop(Shop shop) {
        return new DbContentProvider().delete(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER,
                new String[]{shop.getIdentifier()});
    }

    @Override
    public long addShopping(Shopping singleShoppingItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE, singleShoppingItem.getDate());
        contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE, String.valueOf(singleShoppingItem.getPrice()));
        Cursor productCursor = new DbContentProvider().query(ShoppingDatabaseContract.ProductsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_PRODUCTS,
                ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, new String[]{singleShoppingItem.getBarCode()});
        if (productCursor.moveToFirst()) {
            String productId = productCursor.getString(productCursor.getColumnIndex(ShoppingDatabaseContract.ProductsEntry._ID));
            contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_PRODUTC_BARCODE, productId);
        }
        Cursor shopCusror = new DbContentProvider().query(ShoppingDatabaseContract.ShopsEntry.TABLE, ShoppingDatabaseContract.COLUMNS_NAMES_SHOPS,
                ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, new String[]{singleShoppingItem.getShopIdentifier()});
        if (shopCusror.moveToFirst()) {
            String shopId = shopCusror.getString(productCursor.getColumnIndex(ShoppingDatabaseContract.ShopsEntry._ID));
            contentValues.put(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER, shopId);
        }
        return new DbContentProvider().insert(ShoppingDatabaseContract.MainTableEntry.TABLE, contentValues);
    }

    @Override
    public int deleteShopping(Shopping singleShoppingItem) {
        return  new DbContentProvider().delete(ShoppingDatabaseContract.MainTableEntry.TABLE, ShoppingDatabaseContract.MainTableEntry._ID,
                new String[]{singleShoppingItem.getId()});
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

        protected int delete(String tableName, String selectionString, String[] selectionArgs) {
            database = dbHelper.getWritableDatabase();
            return database.delete(tableName, selectionString, selectionArgs);
        }

    }
}
