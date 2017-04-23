package com.github.w_kamil.shoppingapp.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //TODO check onCreate method, keys (choose _id's or identifiers for tables joins?)


    public DbHelper(Context context, String name, int version) {
        super(context, ShoppingDatabaseContract.DB_NAME, null, ShoppingDatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductsTable = "CREATE TABLE " + ShoppingDatabaseContract.ProductsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " TEXT UNIQUE,"
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION + " TEXT);";
        String createShopsTable = "CREATE TABLE " + ShoppingDatabaseContract.ShopsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ShopsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + " TEXT UNIQUE, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS + " TEXT);";
        String createMainTable = "CREATE TABLE " + ShoppingDatabaseContract.MainTableEntry.TABLE + " ("
                + ShoppingDatabaseContract.MainTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE + " NUMERIC, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE + " TEXT, "
                + ShoppingDatabaseContract.ProductsEntry._ID + " INTEGER FOREIGN KEY, "
                + ShoppingDatabaseContract.ShopsEntry._ID + " INTEGER FOREING KEY);";

        db.execSQL(createProductsTable);
        db.execSQL(createShopsTable);
        db.execSQL(createMainTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
