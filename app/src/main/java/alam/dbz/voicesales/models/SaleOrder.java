package alam.dbz.voicesales.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SaleOrder implements Serializable {
    private String productName;
    private String productID;
    private String qty;
    private String Category;
    private String price;
    private String totalPrice;
    private String salesorderID;
    private String pDiscount;
    private String bID;
    private String unitTypeId;
    private String unitTypeName;
    private String bonusProductId;
    private String bonusProductQty;


    public SaleOrder(String productName, String category, String qty,
                     String price, String totalPrice, String productID,
                     String pDiscount, String unitTypeId, String unitTypeName, String bonusProductId, String bonusProductQty) {
        super();
        this.productName = productName;
        this.productID = productID;
        this.qty = qty;
        this.Category = category;
        this.price = price;
        this.totalPrice = totalPrice;
        this.pDiscount = pDiscount;
        this.unitTypeId = unitTypeId;
        this.unitTypeName = unitTypeName;
        this.bonusProductId = bonusProductId;
        this.bonusProductQty = bonusProductQty;

    }

    public String getBonusProductId() {
        return bonusProductId;
    }

    public String getBonusProductQty() {
        return bonusProductQty;
    }

    public SaleOrder(String productName, String category, String qty,
                     String price, String totalPrice, String productID,
                     String pDiscount, String unitTypeId) {
        super();
        this.productName = productName;
        this.productID = productID;
        this.qty = qty;
        this.Category = category;
        this.price = price;
        this.totalPrice = totalPrice;
        this.pDiscount = pDiscount;
        this.unitTypeId = unitTypeId;

    }

    public SaleOrder(String salesorderID) {
        super();
        this.salesorderID = salesorderID;
    }

    public SaleOrder(String salesorderID, String bID) {
        super();
        this.salesorderID = salesorderID;
        this.bID = bID;
    }

    public String getProductName() {
        return productName;
    }

    public String getQty() {
        return qty;
    }

    public String getCategory() {
        return Category;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getProductID() {
        return productID;
    }

    public String getSalesorderID() {
        return salesorderID;
    }

    public String getpDiscount() {
        return pDiscount;
    }

    public void setpDiscount(String pDiscount) {
        this.pDiscount = pDiscount;
    }

    public String getbID() {
        return bID;
    }

    public void setbID(String bID) {
        this.bID = bID;
    }

    public String getUnitTypeId() {
        return unitTypeId;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

}
