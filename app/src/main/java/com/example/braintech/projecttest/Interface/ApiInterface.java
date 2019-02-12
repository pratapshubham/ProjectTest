package com.example.braintech.projecttest.Interface;

import com.example.braintech.projecttest.Model.CountryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ApiInterface {

    @Headers("appKey:panaceaco@2018Nav")
    @GET("index.php?route=app/account/countries")
    public Call<CountryModel> getCountryname();


}
