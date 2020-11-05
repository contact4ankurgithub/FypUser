package com.example.fyp.UserModel;

public class ShopListModel {

    String id;
    String name;
    String rate;
    String shop_img;
    String business_name;
    String mobile;
    String address;
    String created_on;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public ShopListModel(String id, String name, String rate, String shop_img, String business_name, String mobile, String address, String created_on) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.shop_img = shop_img;
        this.business_name = business_name;
        this.mobile = mobile;
        this.address = address;
        this.created_on = created_on;
    }
}
