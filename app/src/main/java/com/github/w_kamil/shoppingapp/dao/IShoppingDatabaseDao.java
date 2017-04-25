package com.github.w_kamil.shoppingapp.dao;


import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMatchingSpecificShop(Shop shop);
    List<Shop> fetchAllShops();
    List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product product);
    long addProduct(Product product);
    int deleteProduct(Product product);
    long addShop(Shop shop);
    int deleteShop(Shop shop);
    long addShopping(Shopping singleShoppingItem);
    int deleteShopping (Shopping singleShoppingItem);
}
