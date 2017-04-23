package com.github.w_kamil.shoppingapp.dao;


import android.provider.BaseColumns;

public class    ShoppingDatabaseContract {

    public static final String DB_NAME = "shoppingDb";
    public static final int DB_VERSION = 1;


    public class ProductsEntry implements BaseColumns {
        public static final String TABLE = "products";
        public static final String COL_PRODUUCTS_BARCODE = "barcode";
        public static final String COL_PRODUCTS_DESCRIPTION = "description";

    }

    public class ShopsEntry implements BaseColumns {
        public static final String TABLE = "shops";
        public static final String COL_SHOP_IDENTIFIER = "shop_identifier";
        public static final String COL_SHOP_ADDRESS = "shop_address";
    }

    public class MainTableEntry implements BaseColumns {
        public static final String TABLE = "main";
        public static final String COL_MAIN_DATE = "main_date";
        public static final String COL_MAIN_PRICE = "main_price";
    }

    public static String[] COLUMNS_NAMES_PRODUCTS = {ProductsEntry._ID, ProductsEntry.COL_PRODUUCTS_BARCODE, ProductsEntry.COL_PRODUCTS_DESCRIPTION};
    public static String[] COLUMNS_NAMES_SHOPS = {ShopsEntry._ID, ShopsEntry.COL_SHOP_IDENTIFIER, ShopsEntry.COL_SHOP_ADDRESS};
    public static String[] COLUMNS_NAMES_MAIN_TABLE = {MainTableEntry._ID, MainTableEntry.COL_MAIN_DATE, MainTableEntry.COL_MAIN_PRICE, ProductsEntry.COL_PRODUUCTS_BARCODE, ShopsEntry.COL_SHOP_IDENTIFIER};

}
