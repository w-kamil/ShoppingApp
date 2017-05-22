package com.github.w_kamil.shoppingapp.dao;


import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMatchingSpecificShop(String shopIdentifier);
    List<Shop> fetchAllShops();
    List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(String productBarcode);
    long addProduct(Product product);
    int deleteProduct(Product product);
    long addShop(Shop shop);
    int deleteShop(String shopIdentifier);
    boolean searchShopping (String productBarcode);
    long addShopping(Shopping singleShoppingItem);
    int deleteShopping (Shopping singleShoppingItem);
}
