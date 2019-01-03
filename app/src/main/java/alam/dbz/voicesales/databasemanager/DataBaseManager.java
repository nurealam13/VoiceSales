package alam.dbz.voicesales.databasemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import alam.dbz.voicesales.data.Data;
import alam.dbz.voicesales.models.Company;
import alam.dbz.voicesales.models.CompanyModel;
import alam.dbz.voicesales.models.CustomerModel;
import alam.dbz.voicesales.models.ProductModel;
import alam.dbz.voicesales.models.SaleOrder;
import alam.dbz.voicesales.models.UnitType;

public class DataBaseManager {

    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor = null;
    private long db;
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_SALESORDER = "salesorder";
    // private static final String DATABASE_NAME = "SalesOrderTracking.sqlite";
    private String TAG = "DataBaseManager";

    public DataBaseManager(Context context) {

        new Data(context);
        String dbfile = Data.DB_PATH
                + Data.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(dbfile, null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    public ArrayList<CompanyModel> getLocation(String CompanyId) {
        ArrayList<CompanyModel> companies = new ArrayList<>();
        String sqlString = "SELECT * FROM Location WHERE CompanyId='"
                + CompanyId + "'";

        Log.i("TAG", sqlString);
        cursor = sqLiteDatabase.rawQuery(sqlString, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        companies.add(new CompanyModel(cursor.getString(cursor
                                .getColumnIndex("LocationId")), cursor
                                .getString(cursor
                                        .getColumnIndex("LocationName"))));

                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return companies;

    }

    public ArrayList<CompanyModel> getCompanyList() {
        ArrayList<CompanyModel> companyList = new ArrayList<CompanyModel>();
        String sqlString = "SELECT DISTINCT CompanyId,DateOfEntry,CompanyName FROM Company ";

        cursor = sqLiteDatabase.rawQuery(sqlString, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        companyList
                                .add(new CompanyModel(
                                        cursor.getString(cursor
                                                .getColumnIndex("CompanyId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("CompanyName")),
                                        cursor.getString(cursor
                                                .getColumnIndex("DateOfEntry"))));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return companyList;
    }

    public ArrayList<CustomerModel> viewCustomers(String areaId) {

        ArrayList<CustomerModel> customersList = new ArrayList<CustomerModel>();
        String sqlString = "SELECT * FROM customer where AreaId="
                + areaId + "";
        Log.e(Data.TAG, sqlString);
        cursor = sqLiteDatabase.rawQuery(sqlString, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        customersList.add(new CustomerModel(cursor.getString(cursor
                                .getColumnIndex("BuyerId")), cursor
                                .getString(cursor.getColumnIndex("BuyerName")),
                                cursor.getString(cursor
                                        .getColumnIndex("Address")), cursor
                                .getString(cursor
                                        .getColumnIndex("ContactPersonName")),
                                cursor.getString(cursor
                                        .getColumnIndex("CPPhone")), "",
                                cursor.getInt(cursor
                                        .getColumnIndex("giveStatus")),
                                cursor.getString(cursor
                                        .getColumnIndex("generateOrder")),
                                cursor.getString(cursor
                                        .getColumnIndex("AreaId")),
                                cursor.getString(cursor
                                        .getColumnIndex("latitude")),
                                cursor.getString(cursor
                                        .getColumnIndex("longitude"))));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e(Data.TAG, "ERROR: " + e.toString());
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }

        return customersList;

    }

    //    public ArrayList<ProductModel> viewProductsVoice() {
//        ArrayList<ProductModel> productNamesList = new ArrayList<ProductModel>();
//        String sqlString = "SELECT ProductId,ProductName,SKU,PriceUnit,TPUnit,Image,DateOfEntry,Code FROM product";
//
//        Log.i(Data.TAG, sqlString);
//        cursor = sqLiteDatabase.rawQuery(sqlString, null);
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    do {
//
//                        productNamesList
//                                .add(new ProductModel(
//                                        cursor.getInt(cursor
//                                                .getColumnIndex("ProductId")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("ProductName")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("SKU")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("PriceUnit")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("TPUnit")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("Image")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("DateOfEntry")),
//                                        cursor.getString(cursor
//                                                .getColumnIndex("Code"))));
//
//                    } while (cursor.moveToNext());
//                }
//            } finally {
//                cursor.close();
//                sqLiteDatabase.close();
//            }
//        }
//        return productNamesList;
//
//    }

    public String addLocation(String locationId, String CompanyId, String Code,
                              String LocationName, String Address, String DateOfEntry) {

        String result = "";

        String sqlCompany = "INSERT OR REPLACE INTO Location ( LocationId,CompanyId,Code,LocationName,Address, DateOfEntry) VALUES ( ?,?,?,?,?,?)";

        //sqLiteDatabase.beginTransactionNonExclusive();
        try {

            SQLiteStatement stmt = sqLiteDatabase.compileStatement(sqlCompany);

            stmt.bindString(1, locationId);
            stmt.bindString(2, CompanyId);
            stmt.bindString(3, Code);
            stmt.bindString(4, LocationName);
            stmt.bindString(5, Address);
            stmt.bindString(6, DateOfEntry);
            stmt.execute();
            stmt.clearBindings();

            result = "Registration Successfull";
            //	sqLiteDatabase.setTransactionSuccessful();

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("DATABASE", ex.toString());
            result = "Registration Failed";
        } finally {
            //sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();

        }
        return result;
    }

    public String addCompany(String CompanyId, String Code, String Name,
                             String Address, String DateOfEntry) {

        String result = "";

        String sqlCompany = "INSERT OR REPLACE INTO Company ( CompanyId,Code,CompanyName,Address, DateOfEntry) VALUES ( ?,?,?,?,?)";


        try {
            //sqLiteDatabase.beginTransactionNonExclusive();
            SQLiteStatement stmt = sqLiteDatabase.compileStatement(sqlCompany);

            stmt.bindString(1, CompanyId);
            stmt.bindString(2, Code);
            stmt.bindString(3, Name);
            stmt.bindString(4, Address);
            stmt.bindString(5, DateOfEntry);

            stmt.execute();
            stmt.clearBindings();

            result = "Registration Successfull";
            //	sqLiteDatabase.setTransactionSuccessful();

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("DATABASE", ex.toString());
            result = "Registration Failed";
        } finally {
            //	sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();

        }
        return result;
    }

    public boolean CheckLocation(String CompanyId, String locationId) {

        String sqlString = "SELECT * FROM Location WHERE CompanyId='"
                + CompanyId + "' AND locationId='" + locationId + "'";
        cursor = sqLiteDatabase.rawQuery(sqlString, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        return true;

                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return false;

    }

    public ArrayList<UnitType> getUnitTypeList(String productId,
                                               String companyId) {

        ArrayList<UnitType> UnitTypeList = new ArrayList<UnitType>();
        String sqlString = "SELECT UnitType. * FROM UnitType left outer join product ON UnitType .UnitTypeId=Product.UnitTypeId OR UnitType .UnitTypeId=Product.SecondaryUnitTypeId OR UnitType .UnitTypeId=Product.PackagingUnitTypeId where product .productId='"
                + productId + "' AND UnitType .CompanyId='" + companyId + "' AND UnitType.Name <> '0' AND UnitType.Name IS NOT NULL  ";
        cursor = sqLiteDatabase.rawQuery(sqlString, null);
        Log.i(TAG, sqlString);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        UnitTypeList
                                .add(new UnitType(
                                        cursor.getString(cursor
                                                .getColumnIndex("UnitTypeId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("Code")),
                                        cursor.getString(cursor
                                                .getColumnIndex("Name")),
                                        cursor.getString(cursor
                                                .getColumnIndex("CompanyId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("DateOfEntry")),
                                        cursor.getString(cursor
                                                .getColumnIndex("EntryBy"))));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }

        return UnitTypeList;

    }

    public String addLocalProduct(ProductModel productModel, String companyID) {

        String sql = "INSERT INTO Product ( ProductId, ProductName,SKU,PriceUnit,TPUnit,Image,DateOfEntry,Code,UnitTypeId,SecondaryUnitTypeId,PackagingUnitTypeId,GroupId,GroupName,BrandId,BrandName,BonusProductId,Quantity) VALUES (?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String unitType = "INSERT OR REPLACE INTO UnitType ( UnitTypeId, Name,CompanyId) VALUES ( ?,?, ?)";
        sqLiteDatabase.beginTransactionNonExclusive();

        try {
            SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
            SQLiteStatement stmt5 = sqLiteDatabase.compileStatement(unitType);
            stmt.bindString(1, productModel.getProductId());
            stmt.bindString(2, productModel.getName());
            stmt.bindString(3, productModel.getCategory());
            stmt.bindString(4, productModel.getPrice());
            stmt.bindString(5, productModel.getProductDiscount());
            stmt.bindString(6, productModel.getImageURL() + "");
            stmt.bindString(7, productModel.getDateofEntry());
            stmt.bindString(8, productModel.getCode());
            stmt.bindString(9, productModel.getUnitTypeId());
            stmt.bindString(10, productModel.getSecondaryUnitTypeId() + "");
            stmt.bindString(11, productModel.getPackagingUnitTypeId() + "");

            stmt.bindString(12, productModel.getGroupId());
            stmt.bindString(13, productModel.getGroupName());
            stmt.bindString(14, productModel.getBrandId());
            stmt.bindString(15, productModel.getBrandName());
            stmt.bindString(16, productModel.getBonusProductId() + "");
            stmt.bindString(17, productModel.getQuantity() + "");
            stmt.execute();
            stmt.clearBindings();

            if (productModel.getUnitTypeId() != null & productModel.getUnitTypeId() != "") {
                if (getCount(productModel.getUnitTypeId(), productModel.getUnitType()) == 0) {
                    stmt5.bindString(1, productModel.getUnitTypeId());
                    stmt5.bindString(2, productModel.getUnitType());
                    stmt5.bindString(3, companyID);
                    stmt5.execute();
                    stmt5.clearBindings();
                }
            }
            if (productModel.getSecondaryUnitTypeId() != null & productModel.getSecondaryUnitTypeId() != "") {
                if (getCount(productModel.getSecondaryUnitTypeId(), productModel.getSecondaryUnitType()) == 0) {
                    stmt5.bindString(1, productModel.getSecondaryUnitTypeId());
                    stmt5.bindString(2, productModel.getSecondaryUnitType()+"");
                    stmt5.bindString(3, companyID);
                    stmt5.execute();
                    stmt5.clearBindings();
                }
            }
            if (productModel.getPackagingUnitTypeId() != null & productModel.getPackagingUnitTypeId() != "") {

                if (getCount(productModel.getPackagingUnitTypeId(), productModel.getPackagingUnitType()) == 0) {

                    stmt5.bindString(1, productModel.getPackagingUnitTypeId());
                    stmt5.bindString(2, productModel.getPackagingUnitType());
                    stmt5.bindString(3, companyID);
                    stmt5.execute();
                    stmt5.clearBindings();
                }
            }

            Log.e(Data.TAG, "Successful Insert: ");
            sqLiteDatabase.setTransactionSuccessful();
            return "1";
        } catch (Exception ex) {
            Log.e(Data.TAG, "ERROR: " + ex.toString());
            ex.printStackTrace();
            return "0";
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();

        }
    }

    public ArrayList<ProductModel> viewProductsVoice() {
        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();
        String sqlString = "SELECT p.*,(SELECT Name from UnitType WHere p.UnitTypeId=UnitTypeId) AS UnitTypeName,(SELECT Name from UnitType WHere p.SecondaryUnitTypeId=UnitTypeId) AS SecondaryUnitType,(SELECT Name from UnitType WHere p.PackagingUnitTypeId=UnitTypeId) AS PackagingUnitType  FROM product p ";

        Log.i(Data.TAG, sqlString);
        cursor = sqLiteDatabase.rawQuery(sqlString, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {

                        productList
                                .add(new ProductModel(
                                        cursor.getString(cursor
                                                .getColumnIndex("ProductId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("ProductName")),
                                        cursor.getString(cursor
                                                .getColumnIndex("Code")),
                                        cursor.getString(cursor
                                                .getColumnIndex("SKU")),
                                        cursor.getString(cursor
                                                .getColumnIndex("PriceUnit")),
                                        cursor.getString(cursor
                                                .getColumnIndex("Image")),
                                        cursor.getString(cursor
                                                .getColumnIndex("DateOfEntry")),
                                        cursor.getString(cursor
                                                .getColumnIndex("TPUnit")),
                                        cursor.getString(cursor
                                                .getColumnIndex("GroupId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("GroupName")),
                                        cursor.getString(cursor
                                                .getColumnIndex("BrandId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("BrandName")),
                                        cursor.getString(cursor
                                                .getColumnIndex("UnitTypeId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("UnitTypeName")),
                                        cursor.getString(cursor
                                                .getColumnIndex("SecondaryUnitTypeId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("PackagingUnitTypeId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("SecondaryUnitType")),
                                        cursor.getString(cursor
                                                .getColumnIndex("PackagingUnitType")),
                                        cursor.getString(cursor
                                                .getColumnIndex("BonusProductId")),
                                        cursor.getString(cursor
                                                .getColumnIndex("Quantity"))));

                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.i(Data.TAG, e.toString());
            } finally {
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return productList;

    }

    public String addLocalCustomer(CustomerModel c, String areaId) {

        String sql = "INSERT INTO customer ( BuyerId, BuyerName,Address,ContactPersonName,CPPhone,Image,giveStatus,generateOrder,DateOfEntry,AreaId,latitude,longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //	sqLiteDatabase.beginTransactionNonExclusive();

        try {
            SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
            stmt.bindString(1, c.getBuyerId());
            stmt.bindString(2, c.getName());
            stmt.bindString(3, c.getAddress()+"");
            stmt.bindString(4, c.getContactPerson() + "");
            stmt.bindString(5, c.getCPPhone() + "");
            stmt.bindString(6, "");
            stmt.bindString(7, "0");
            stmt.bindString(8, c.getGenerateOrder() + "");
            stmt.bindString(9, "");
            stmt.bindString(10, areaId);
            stmt.bindString(11, c.getLat() + "");
            stmt.bindString(12, c.getLongitude() + "");
            stmt.execute();
            stmt.clearBindings();
            //sqLiteDatabase.setTransactionSuccessful();
            Log.e(Data.TAG, "Inserted: "+areaId);
            return "1";
        } catch (Exception ex) {
            Log.e(Data.TAG, "Error: " + ex.toString());
            ex.printStackTrace();
            return "0";
        } finally {
            //	sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();

        }
    }

    public int getCount(String id, String name) {
        Cursor c = null;
        try {
            String sql = "SELECT * FROM UnitType WHERE UnitTypeId='" + id
                    + "' AND Name='" + name + "'";
            c = sqLiteDatabase.rawQuery(sql, null);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public String addLocalSalesOrderDetails(SaleOrder s, String userId) {

        String sql = "INSERT INTO salesorderdetail ( SalesOrderId, ProductId,OrderQty,TPDiscount,Price,DateofEntry,UserId,UnittypeId,BonusProductId,BonusProductQty) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.beginTransactionNonExclusive();

        try {
            SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
            stmt.bindString(1, s.getSalesorderID());
            stmt.bindString(2, s.getProductID());
            stmt.bindString(3, s.getQty());
            stmt.bindString(4, s.getpDiscount());
            stmt.bindString(5, s.getPrice());
            stmt.bindString(6, getDisplayDate());
            stmt.bindString(7, userId);
            stmt.bindString(8, s.getUnitTypeId());
            stmt.bindString(9, s.getBonusProductId());
            stmt.bindString(10, s.getBonusProductQty());

            stmt.execute();
            stmt.clearBindings();
            sqLiteDatabase.setTransactionSuccessful();
            return "1";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();

        }
    }


    private String getDisplayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}
