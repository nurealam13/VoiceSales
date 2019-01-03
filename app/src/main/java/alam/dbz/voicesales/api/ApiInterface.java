package alam.dbz.voicesales.api;


import java.util.ArrayList;
import java.util.List;

import alam.dbz.voicesales.models.CompanyListModel;
import alam.dbz.voicesales.models.CustomerListModel;
import alam.dbz.voicesales.models.LoginModel;
import alam.dbz.voicesales.models.ProductListModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("index-br2-voice.php")
    Call<CompanyListModel> getCompany(
            @Query("method") String method
    );

    @GET("index-br2-voice.php")
    Call<LoginListModel> getLoginStatus(
            @Query("method") String method, @Query("companyId") String companyId, @Query("locationId") String locationId, @Query("UserName") String UserName, @Query("Password") String Password, @Query("Day") String Day, @Query("IMEINo") String IMEINo
    );

    @GET("index-br2-voice.php")
    Call<ProductListModel> getProducts(
            @Query("method") String method, @Query("UserId") String UserId, @Query("companyId") String companyId
    );
    @GET("index-br2-voice.php")
    Call<CustomerListModel> getCustomers(
            @Query("method") String method, @Query("AreaId") String AreaId, @Query("CompanyId") String companyId
    );
}
