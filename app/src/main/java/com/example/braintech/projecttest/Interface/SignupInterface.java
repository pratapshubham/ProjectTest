package com.example.braintech.projecttest.Interface;

import com.example.braintech.projecttest.Model.HomeFragmentModel;
import com.example.braintech.projecttest.Model.StateModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupInterface {

    @Headers("appKey:panaceaco@2018Nav")
    @POST("index.php?route=app/account/zone")
    Call<StateModel> getAccess(@Header("Content-Type")
                                              String contentType,
                               @Header("Cache-Control")
                                              String cache,
                               @Body RequestBody params);
}
