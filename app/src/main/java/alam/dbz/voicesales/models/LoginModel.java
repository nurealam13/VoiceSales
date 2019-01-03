package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("success")
    private String success;
    @SerializedName("AreaName")
    private String AreaName;
    @SerializedName("AreaId")
    private String AreaId;
    @SerializedName("DistributorName")
    private String DistributorName;
    @SerializedName("DistributorId")
    private String DistributorId;
    @SerializedName("userId")
    private String userId;

    public String getSuccess() {
        return success;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getAreaId() {
        return AreaId;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
