package alam.dbz.voicesales.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {


    public static ApiInterface getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://databizsoftware.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; // return the APIInterface object
    }
}
