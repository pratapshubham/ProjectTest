package com.example.braintech.projecttest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Apiclient.HomeApiClient;
import com.example.braintech.projecttest.Interface.HomeApiInterface;
import com.example.braintech.projecttest.Model.HomeFragmentModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

TextView txt_pageTitle,txt_details;
private HomeApiInterface homeApiInterface;
String title,discription;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        txt_pageTitle = (TextView)view.findViewById(R.id.txt_pagetitle);
        txt_details = (TextView)view.findViewById(R.id.txt_discription);
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
                    title = homeFragmentModel.getData().getFilterData().getPageTitle();
                    discription = homeFragmentModel.getData().getFilterData().getDescription();
                    txt_pageTitle.setText(title);
                    txt_details.setText(discription);
                    System.out.println("Title========>>"+title);
                   // Toast.makeText(getContext(),"Successfull",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentModel> call, Throwable t) {

                Toast.makeText(getContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
