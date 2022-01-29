package com.devbramm.mitumba.Models;

public class AddressRequest {

    private String addressRequestFirstName;
    private String addressRequestLastName;
    private String addressRequestLocation;
    private String addressRequestAdditionalInfo;
    private String addressRequestCounty;
    private String addressRequestMobilePhone;
    private String addressRequestAdditionalPhone;
    private Boolean addressRequestDefaultAddress;

    public AddressRequest() {
    }

    public AddressRequest(String addressRequestFirstName, String addressRequestLastName, String addressRequestLocation, String addressRequestAdditionalInfo, String addressRequestCounty, String addressRequestMobilePhone, String addressRequestAdditionalPhone, Boolean addressRequestDefaultAddress) {
        this.addressRequestFirstName = addressRequestFirstName;
        this.addressRequestLastName = addressRequestLastName;
        this.addressRequestLocation = addressRequestLocation;
        this.addressRequestAdditionalInfo = addressRequestAdditionalInfo;
        this.addressRequestCounty = addressRequestCounty;
        this.addressRequestMobilePhone = addressRequestMobilePhone;
        this.addressRequestAdditionalPhone = addressRequestAdditionalPhone;
        this.addressRequestDefaultAddress = addressRequestDefaultAddress;
    }

    public String getAddressRequestFirstName() {
        return addressRequestFirstName;
    }

    public void setAddressRequestFirstName(String addressRequestFirstName) {
        this.addressRequestFirstName = addressRequestFirstName;
    }

    public String getAddressRequestLastName() {
        return addressRequestLastName;
    }

    public void setAddressRequestLastName(String addressRequestLastName) {
        this.addressRequestLastName = addressRequestLastName;
    }

    public String getAddressRequestLocation() {
        return addressRequestLocation;
    }

    public void setAddressRequestLocation(String addressRequestLocation) {
        this.addressRequestLocation = addressRequestLocation;
    }

    public String getAddressRequestAdditionalInfo() {
        return addressRequestAdditionalInfo;
    }

    public void setAddressRequestAdditionalInfo(String addressRequestAdditionalInfo) {
        this.addressRequestAdditionalInfo = addressRequestAdditionalInfo;
    }

    public String getAddressRequestCounty() {
        return addressRequestCounty;
    }

    public void setAddressRequestCounty(String addressRequestCounty) {
        this.addressRequestCounty = addressRequestCounty;
    }

    public String getAddressRequestMobilePhone() {
        return addressRequestMobilePhone;
    }

    public void setAddressRequestMobilePhone(String addressRequestMobilePhone) {
        this.addressRequestMobilePhone = addressRequestMobilePhone;
    }

    public String getAddressRequestAdditionalPhone() {
        return addressRequestAdditionalPhone;
    }

    public void setAddressRequestAdditionalPhone(String addressRequestAdditionalPhone) {
        this.addressRequestAdditionalPhone = addressRequestAdditionalPhone;
    }

    public Boolean getAddressRequestDefaultAddress() {
        return addressRequestDefaultAddress;
    }

    public void setAddressRequestDefaultAddress(Boolean addressRequestDefaultAddress) {
        this.addressRequestDefaultAddress = addressRequestDefaultAddress;
    }
}
