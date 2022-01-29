package com.devbramm.mitumba.Models.PaymentMethodsModels;

public class CashOnDelivery {

    private String cartID;
    private String cartAmount;
    private String receiverName;
    private String receiverPhone;


    public CashOnDelivery() {
    }

    public CashOnDelivery(String cartID, String cartAmount, String receiverName, String receiverPhone) {
        this.cartID = cartID;
        this.cartAmount = cartAmount;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(String cartAmount) {
        this.cartAmount = cartAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
