package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 8/28/2018.
 */

public class CompanyListModel {
    @SerializedName("data")
    private ArrayList<CompanyModel> companyModelArrayList=new ArrayList<>();

    public ArrayList<CompanyModel> getCompanyModelArrayList() {
        return companyModelArrayList;
    }

    public void setCompanyModelArrayList(ArrayList<CompanyModel> companyModelArrayList) {
        this.companyModelArrayList = companyModelArrayList;
    }
}
