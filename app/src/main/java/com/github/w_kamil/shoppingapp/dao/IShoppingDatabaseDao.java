package com.github.w_kamil.shoppingapp.dao;


import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMachingSpecificShopIdentifier();
    List<Shop> fetchsAllShops();
    List<Shopping> fetchShoppingMatchingSpecfingBarcode();
    boolean addProduct();
    boolean deleteProduct();
    boolean addShop();
    boolean deleteShop();
    boolean addShopping();
}
