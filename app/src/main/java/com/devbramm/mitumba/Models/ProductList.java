package com.devbramm.mitumba.Models;

public class ProductList {

    private String ItemName;
    private String ItemPrice;
    private String ItemRating;
    private String ItemImage;

    public ProductList() {
    }

    public ProductList(String itemName, String itemPrice, String itemRating, String itemImage) {
        ItemName = itemName;
        ItemPrice = itemPrice;
        ItemRating = itemRating;
        ItemImage = itemImage;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemRating() {
        return ItemRating;
    }

    public void setItemRating(String itemRating) {
        ItemRating = itemRating;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }
}
