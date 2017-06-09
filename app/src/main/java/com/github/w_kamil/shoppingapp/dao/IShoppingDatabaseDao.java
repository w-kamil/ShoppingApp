package com.github.w_kamil.shoppingapp.dao;

import java.util.List;

public interface IShoppingDatabaseDao {

    List<Product> fetchAllProducts();
    List<Product> fetchAllProductsMatchingSpecificShop(Shop shopToSearch);
    List<Shop> fetchAllShops();
    List<Shopping> fetchAllShoppingItemsMatchingSpecificProduct(Product productToSearch);
    long addProduct(Product product);
    int deleteProduct(Product product);
    long addShop(Shop shop);
    int renameShop (Shop shop, String newShopName);
    int changeShopAddress (Shop shop, String newShopAddress);
    int deleteShop(Shop shopToDeleteFromDb);
    Product searchProduct (Integer productToSearchId);
    Product searchProductByBarcode(String barcode);
    Shop searchShop (Integer shopToSearchId);
    long addShopping(Shopping singleShoppingItem);
    int deleteShopping (Shopping singleShoppingItem);
}
