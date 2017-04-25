package com.github.w_kamil.shoppingapp.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //TODO ckeys (choose _id's or identifiers more useful for tables joins?)


    public DbHelper(Context context) {
        super(context, ShoppingDatabaseContract.DB_NAME, null, ShoppingDatabaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductsTable = "CREATE TABLE " + ShoppingDatabaseContract.ProductsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ProductsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUUCTS_BARCODE + " TEXT NOT NULL UNIQUE,"
                + ShoppingDatabaseContract.ProductsEntry.COL_PRODUCTS_DESCRIPTION + " TEXT);";
        String createShopsTable = "CREATE TABLE " + ShoppingDatabaseContract.ShopsEntry.TABLE + " ("
                + ShoppingDatabaseContract.ShopsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER + " TEXT NOT NULL UNIQUE, "
                + ShoppingDatabaseContract.ShopsEntry.COL_SHOP_ADDRESS + " TEXT);";
        String createMainTable = "CREATE TABLE " + ShoppingDatabaseContract.MainTableEntry.TABLE + " ("
                + ShoppingDatabaseContract.MainTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_DATE + " TEXT NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_MAIN_PRICE + " TEXT NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTCS_ID + " INTEGER NOT NULL, "
                + ShoppingDatabaseContract.MainTableEntry.COL_SHOPS_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + ShoppingDatabaseContract.MainTableEntry.COL_PRODUTCS_ID + ") REFERENCES " + ShoppingDatabaseContract.ProductsEntry.TABLE + " (" + ShoppingDatabaseContract.ProductsEntry._ID + "), "
                + "FOREIGN KEY (" + ShoppingDatabaseContract.MainTableEntry.COL_SHOPS_ID + ") REFERENCES " + ShoppingDatabaseContract.ShopsEntry.TABLE + " (" + ShoppingDatabaseContract.ShopsEntry._ID + "));";

        db.execSQL(createProductsTable);
        db.execSQL(createShopsTable);
        db.execSQL(createMainTable);

        //TODO delete these lines - used only for DbHelper test
        ContentValues values = new ContentValues();
        values.put(ShoppingDatabaseContract.ShopsEntry.COL_SHOP_IDENTIFIER, "abc");
        db.insert(ShoppingDatabaseContract.ShopsEntry.TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
