package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompanyModel implements Serializable {
    @SerializedName("CompanyId")
    private String CompanyId;
    @SerializedName("CompanyName")
    private String CompanyName;
    @SerializedName("Code")
    private String Code;
    @SerializedName("LocationId")
    private String LocationId;
    @SerializedName("LocationName")
    private String LocationName;

    private String DateOfEntry;

    public CompanyModel(String companyId, String companyName, String dateOfEntry) {
        CompanyId = companyId;
        CompanyName = companyName;
        DateOfEntry = dateOfEntry;
    }

    public CompanyModel(String locationId, String locationName) {
        LocationId = locationId;
        LocationName = locationName;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getCode() {
        return Code;
    }

    public String getLocationId() {
        return LocationId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public String getDateOfEntry() {
        return DateOfEntry;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public void setDateOfEntry(String dateOfEntry) {
        DateOfEntry = dateOfEntry;
    }
}
