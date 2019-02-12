package com.example.braintech.projecttest;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Apiclient.HomeApiClient;
import com.example.braintech.projecttest.Interface.HomeApiInterface;
import com.example.braintech.projecttest.Model.HomeFragmentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    TextView txt_pageTitle;
    ImageView img_product;
    private HomeApiInterface homeApiInterface;
    String title,productName,productDiscription,productCost;
    ProgressDialog progressDialog;
    View view;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<HomeFragmentModel.Data.Product> productsArrayList ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_home, container, false);
         getallId();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDetails();


    }



    private void getDetails()
    {

        homeApiInterface = HomeApiClient.getClient().create(HomeApiInterface.class);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();



        setHomeAdapter();

    }

    public void setHomeAdapter()
    {
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.get("page_title");
            jsonObject.get("description");
            jsonObject.put("page",0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (jsonObject.toString()));

        Call<HomeFragmentModel> getHome = homeApiInterface.getAccess("application/json",
                "no-cache", body);

        getHome.enqueue(new Callback<HomeFragmentModel>() {
            @Override
            public void onResponse(Call<HomeFragmentModel> call, Response<HomeFragmentModel> response) {



                HomeFragmentModel homeFragmentModel = response.body();

                Log.d("Response----->", String.valueOf(response.code()));
                if (response.isSuccessful())
                {


                    productsArrayList=homeFragmentModel.getData().getProducts();


                    System.out.println("Total Product Size-------->"+productsArrayList.size());
                    homeAdapter = new HomeAdapter(productsArrayList,getContext());
                    recyclerView.setAdapter(homeAdapter);
                    progressDialog.dismiss();

                    /*homeAdapter=new */

                }
            }

            @Override
            public void onFailure(Call<HomeFragmentModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void getallId()
    {

        img_product = (ImageView)view.findViewById(R.id.img_product);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        productsArrayList = new ArrayList<>();


    }


}
