package alam.dbz.voicesales.models;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("ProductId")
    private String ProductId;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Code")
    private String Code;
    @SerializedName("Category")
    private String Category;
    @SerializedName("Price")
    private String Price;
    @SerializedName("ImageURL")
    private String ImageURL;
    @SerializedName("DateofEntry")
    private String DateofEntry;
    @SerializedName("ProductDiscount")
    private String ProductDiscount;
    @SerializedName("GroupId")
    private String GroupId;
    @SerializedName("GroupName")
    private String GroupName;
    @SerializedName("BrandId")
    private String BrandId;
    @SerializedName("BrandName")
    private String BrandName;
    @SerializedName("UnitTypeId")
    private String UnitTypeId;
    @SerializedName("UnitType")
    private String UnitType;
    @SerializedName("SecondaryUnitTypeId")
    private String SecondaryUnitTypeId;
    @SerializedName("PackagingUnitTypeId")
    private String PackagingUnitTypeId;
    @SerializedName("SecondaryUnitType")
    private String SecondaryUnitType;
    @SerializedName("PackagingUnitType")
    private String PackagingUnitType;
    @SerializedName("BonusProductId")
    private String BonusProductId;
    @SerializedName("Quantity")
    private String Quantity;

    public ProductModel(String productId, String name, String code, String category, String price, String imageURL, String dateofEntry, String productDiscount, String groupId, String groupName, String brandId, String brandName, String unitTypeId, String unitType, String secondaryUnitTypeId, String packagingUnitTypeId, String secondaryUnitType, String packagingUnitType, String bonusProductId, String quantity) {
        ProductId = productId;
        Name = name;
        Code = code;
        Category = category;
        Price = price;
        ImageURL = imageURL;
        DateofEntry = dateofEntry;
        ProductDiscount = productDiscount;
        GroupId = groupId;
        GroupName = groupName;
        BrandId = brandId;
        BrandName = brandName;
        UnitTypeId = unitTypeId;
        UnitType = unitType;
        SecondaryUnitTypeId = secondaryUnitTypeId;
        PackagingUnitTypeId = packagingUnitTypeId;
        SecondaryUnitType = secondaryUnitType;
        PackagingUnitType = packagingUnitType;
        BonusProductId = bonusProductId;
        Quantity = quantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    public String getCategory() {
        return Category;
    }

    public String getPrice() {
        return Price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getDateofEntry() {
        return DateofEntry;
    }

    public String getProductDiscount() {
        return ProductDiscount;
    }

    public String getGroupId() {
        return GroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getBrandId() {
        return BrandId;
    }

    public String getBrandName() {
        return BrandName;
    }

    public String getUnitTypeId() {
        return UnitTypeId;
    }

    public String getUnitType() {
        return UnitType;
    }

    public String getSecondaryUnitTypeId() {
        return SecondaryUnitTypeId;
    }

    public String getPackagingUnitTypeId() {
        return PackagingUnitTypeId;
    }

    public String getSecondaryUnitType() {
        return SecondaryUnitType;
    }

    public String getPackagingUnitType() {
        return PackagingUnitType;
    }

    public String getBonusProductId() {
        return BonusProductId;
    }

    public String getQuantity() {
        return Quantity;
    }
}
