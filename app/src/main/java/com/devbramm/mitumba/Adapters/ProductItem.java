package com.devbramm.mitumba.Adapters;

public class ProductItem {

    private String ProductName;
    private String ProductPrice;
    private String ProductRating;
    private int ProductImage;

    public ProductItem() {

    }

    public ProductItem(String name, String price, String rating, int image) {
        ProductName = name;
        ProductPrice = price;
        ProductRating = rating;
        ProductImage = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductRating() {
        return ProductRating;
    }

    public void setProductRating(String productRating) {
        ProductRating = productRating;
    }

    public int getProductImage() {
        return ProductImage;
    }

    public void setProductImage(int productImage) {
        ProductImage = productImage;
    }
}
