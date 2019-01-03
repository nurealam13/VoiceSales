package alam.dbz.voicesales.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import alam.dbz.voicesales.models.CompanyModel;
import alam.dbz.voicesales.models.LoginModel;

public class LoginListModel {
    @SerializedName("data")
    private ArrayList<LoginModel> loginModels = new ArrayList<>();

    public ArrayList<LoginModel> getLoginModels() {
        return loginModels;
    }

    public void setLoginModels(ArrayList<LoginModel> loginModels) {
        this.loginModels = loginModels;
    }
}
