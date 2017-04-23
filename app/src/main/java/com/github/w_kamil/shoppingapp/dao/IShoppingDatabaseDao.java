package com.github.w_kamil.shoppingapp.dao;


import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMatchingSpecificShop(Shop shop);
    List<Shop> fetchAllShops();
    List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product product);
    boolean addProduct(Product product);
    boolean deleteProduct(Product product);
    boolean addShop(Shop shop);
    boolean deleteShop(Shop shop);
    boolean addShopping(Shopping singleShoppingItem);
    boolean deleteShopping (Shopping singleShoppingItem);
}
