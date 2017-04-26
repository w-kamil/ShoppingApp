package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, ShoppingDatabaseContract.DB_NAME, null, ShoppingDatabaseContract.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductsTable = "CREATE TABLE " + ShoppingDatabaseContract.ProductsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " TEXT NOT NULL ,"
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION + " TEXT);";
        String createShopsTable = "CREATE TABLE " + ShoppingDatabaseContract.ShopsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ShopsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + " TEXT NOT NULL, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS + " TEXT);";
        String createMainTable = "CREATE TABLE " + ShoppingDatabaseContract.MainTableEntry.TABLE + " ("
                + ShoppingDatabaseContract.MainTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE + " INT NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE + " TEXT NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE + " TEXT NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER + " TEXT NOT NULL, "
                + "FOREIGN KEY (" + ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE + ") REFERENCES "
                + ShoppingDatabaseContract.ProductsEntry.TABLE + " (" + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + "), "
                + "FOREIGN KEY (" + ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER + ") REFERENCES "
                + ShoppingDatabaseContract.ShopsEntry.TABLE + " (" + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + "));";

        db.execSQL(createProductsTable);
        db.execSQL(createShopsTable);
        db.execSQL(createMainTable);

        //TODO delete these lines - used only for DbHelper test
        ContentValues valuesShop = new ContentValues();
        valuesShop.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, "abc");
        db.insert(ShoppingDatabaseContract.ShopsEntry.TABLE, null, valuesShop);
        ContentValues valuesProduct = new ContentValues();
        valuesProduct.put(ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE, "59045");
        db.insert(ShoppingDatabaseContract.ProductsEntry.TABLE, null, valuesProduct);
        ContentValues valuesMain = new ContentValues();
        valuesMain.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE, 100000);
        valuesMain.put(ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE, 10);
        valuesMain.put(ShoppingDatabaseContract.MainTableEntry.COL_PRODUCT_BARCODE, "59045");
        valuesMain.put(ShoppingDatabaseContract.MainTableEntry.COL_SHOP_IDENTIFIER, "abc");
        db.insert(ShoppingDatabaseContract.MainTableEntry.TABLE, null, valuesMain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
