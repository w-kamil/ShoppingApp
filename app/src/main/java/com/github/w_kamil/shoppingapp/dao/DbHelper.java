package com.github.w_kamil.shoppingapp.dao;

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
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " TEXT UNIQUE NOT NULL ,"
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION + " TEXT);";
        String createShopsTable = "CREATE TABLE " + ShoppingDatabaseContract.ShopsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ShopsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + " TEXT UNIQUE NOT NULL, "
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
