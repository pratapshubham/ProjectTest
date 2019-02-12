package com.example.braintech.projecttest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.braintech.projecttest.Apiclient.HomeApiClient;
import com.example.braintech.projecttest.Interface.HomeApiInterface;
import com.example.braintech.projecttest.Model.HomeFragmentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetProductInfo extends ViewModel {

    private MutableLiveData<List<HomeFragmentModel.Data.Product>> infoList;

    public LiveData<List<HomeFragmentModel.Data.Product>> getinfo() {

        if (infoList == null)
        {
            infoList = new MutableLiveData<List<HomeFragmentModel.Data.Product>>();

            loadinfoList();
        }
        return infoList;
    }

    private void loadinfoList()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.panaceaco.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        HomeApiInterface homeApiInterface = retrofit.create(HomeApiInterface.class);

        Call<List<HomeFragmentModel.Data.Product>> call = homeApiInterface.getproduct();

        call.enqueue(new Callback<List<HomeFragmentModel.Data.Product>>() {
            @Override
            public void onResponse(Call<List<HomeFragmentModel.Data.Product>> call, Response<List<HomeFragmentModel.Data.Product>> response) {
                infoList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<HomeFragmentModel.Data.Product>> call, Throwable t) {

            }
        });


    }
}
