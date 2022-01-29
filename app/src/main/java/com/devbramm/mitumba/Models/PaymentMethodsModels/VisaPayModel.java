package com.devbramm.mitumba.Models.PaymentMethodsModels;

public class VisaPayModel {

    private String cartID;
    private String cartAmount;
    private String visaCreditCardNumber;
    private String visaMMYY;
    private String visaCVV;
    private String visaCardName;


    public VisaPayModel() {
    }

    public VisaPayModel(String cartID, String cartAmount, String visaCreditCardNumber, String visaMMYY, String visaCVV, String visaCardName) {
        this.cartID = cartID;
        this.cartAmount = cartAmount;
        this.visaCreditCardNumber = visaCreditCardNumber;
        this.visaMMYY = visaMMYY;
        this.visaCVV = visaCVV;
        this.visaCardName = visaCardName;
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

    public String getVisaCreditCardNumber() {
        return visaCreditCardNumber;
    }

    public void setVisaCreditCardNumber(String visaCreditCardNumber) {
        this.visaCreditCardNumber = visaCreditCardNumber;
    }

    public String getVisaMMYY() {
        return visaMMYY;
    }

    public void setVisaMMYY(String visaMMYY) {
        this.visaMMYY = visaMMYY;
    }

    public String getVisaCVV() {
        return visaCVV;
    }

    public void setVisaCVV(String visaCVV) {
        this.visaCVV = visaCVV;
    }

    public String getVisaCardName() {
        return visaCardName;
    }

    public void setVisaCardName(String visaCardName) {
        this.visaCardName = visaCardName;
    }
}
