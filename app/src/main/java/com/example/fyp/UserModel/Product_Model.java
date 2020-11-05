package com.example.fyp.UserModel;

public class Product_Model {
    String id;
    String barcode_id;
    String merchant_id;
    String product_name;
    String product_img;
    String main_id;
    String sub_id;
    String sale_price;
    String discount;
    String tag;
    String quantity;
    String description;
    String offer;
    String created_on;
    String update_on;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode_id() {
        return barcode_id;
    }

    public void setBarcode_id(String barcode_id) {
        this.barcode_id = barcode_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }

    public Product_Model(String id, String barcode_id, String merchant_id, String product_name, String product_img, String main_id, String sub_id, String sale_price, String discount, String tag, String quantity, String description, String offer, String created_on, String update_on) {
        this.id = id;
        this.barcode_id = barcode_id;
        this.merchant_id = merchant_id;
        this.product_name = product_name;
        this.product_img = product_img;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.sale_price = sale_price;
        this.discount = discount;
        this.tag = tag;
        this.quantity = quantity;
        this.description = description;
        this.offer = offer;
        this.created_on = created_on;
        this.update_on = update_on;
    }
}
