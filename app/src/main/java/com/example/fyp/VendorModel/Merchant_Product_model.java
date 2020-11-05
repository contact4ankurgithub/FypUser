package com.example.fyp.VendorModel;

public class Merchant_Product_model {

    private String barcode_id;
    private String product_name;
    private String product_img;
    private String main_id;
    private String sub_id;
    private String sale_price;
    private String quantity;

    public String getBarcode_id() {
        return barcode_id;
    }

    public void setBarcode_id(String barcode_id) {
        this.barcode_id = barcode_id;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    private String description;
    private String availability;

    public Merchant_Product_model(String barcode_id, String product_name, String product_img, String main_id, String sub_id, String sale_price, String quantity, String description, String availability) {
        this.barcode_id = barcode_id;
        this.product_name = product_name;
        this.product_img = product_img;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.sale_price = sale_price;
        this.quantity = quantity;
        this.description = description;
        this.availability = availability;
    }
}
