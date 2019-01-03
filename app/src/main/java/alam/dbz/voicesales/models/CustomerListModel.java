package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerListModel {
    @SerializedName("data")
    private ArrayList<CustomerModel> customerModels=new ArrayList<>();

    public ArrayList<CustomerModel> getCustomerModels() {
        return customerModels;
    }

    public void setCustomerModels(ArrayList<CustomerModel> customerModels) {
        this.customerModels = customerModels;
    }
}
