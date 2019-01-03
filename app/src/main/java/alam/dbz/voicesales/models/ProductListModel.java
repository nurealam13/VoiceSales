package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductListModel {
    @SerializedName("data")
    private ArrayList<ProductModel> productModels = new ArrayList<>();

    public ArrayList<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }
}
