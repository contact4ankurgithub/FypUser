package com.example.fyp.UserModel;

public class Wishlist_Model {
    String product_name;
    String product_price;
    String product_quantity;

    public Wishlist_Model(String productName, String product_name, String product_price) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }
}
