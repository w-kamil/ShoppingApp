package com.github.w_kamil.shoppingapp.dao;


import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMachingSpecificShopIdentifier(String indentifier);
    List<Shop> fetchsAllShops();
    List<Shopping> fetchShoppingMatchingSpecfingBarcode(String barcode);
    boolean addProduct(Product product);
    boolean deleteProduct(Product product);
    boolean addShop(Shop shop);
    boolean deleteShop(Shop shop);
    boolean addShopping(Shopping shopping);
}
