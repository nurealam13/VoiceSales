package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerModel implements Serializable {
    @SerializedName("BuyerId")
    private String BuyerId;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Address")
    private String Address;
    @SerializedName("ContactPerson")
    private String ContactPerson;
    @SerializedName("CPPhone")
    private String CPPhone;
    @SerializedName("Email")
    private String Email;
    @SerializedName("giveStatus")
    private int giveStatus;
    @SerializedName("generateOrder")
    private String generateOrder;
    @SerializedName("AreaId")
    private String AreaId;
    @SerializedName("Lat")
    private String Lat;
    @SerializedName("Longitude")
    private String Longitude;

    public CustomerModel(String buyerId, String name, String address, String contactPerson, String CPPhone, String email, int giveStatus, String generateOrder, String areaId, String lat, String longitude) {
        BuyerId = buyerId;
        Name = name;
        Address = address;
        ContactPerson = contactPerson;
        this.CPPhone = CPPhone;
        Email = email;
        this.giveStatus = giveStatus;
        this.generateOrder = generateOrder;
        AreaId = areaId;
        Lat = lat;
        Longitude = longitude;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public String getCPPhone() {
        return CPPhone;
    }

    public String getEmail() {
        return Email;
    }

    public int getGiveStatus() {
        return giveStatus;
    }

    public String getGenerateOrder() {
        return generateOrder;
    }

    public String getAreaId() {
        return AreaId;
    }

    public String getLat() {
        return Lat;
    }

    public String getLongitude() {
        return Longitude;
    }
}
