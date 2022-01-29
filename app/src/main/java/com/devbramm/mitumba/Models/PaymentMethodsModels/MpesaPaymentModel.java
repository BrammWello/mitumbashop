package com.devbramm.mitumba.Models.PaymentMethodsModels;

public class MpesaPaymentModel {

    private String cartID;
    private String cartAmount;
    private String mpesaPhone;

    public MpesaPaymentModel() {
    }

    public MpesaPaymentModel(String cartID, String cartAmount, String mpesaPhone) {
        this.cartID = cartID;
        this.cartAmount = cartAmount;
        this.mpesaPhone = mpesaPhone;
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

    public String getMpesaPhone() {
        return mpesaPhone;
    }

    public void setMpesaPhone(String mpesaPhone) {
        this.mpesaPhone = mpesaPhone;
    }
}
