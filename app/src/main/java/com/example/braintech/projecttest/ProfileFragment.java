package com.example.braintech.projecttest;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.braintech.projecttest.Model.UserModel;

public class ProfileFragment extends Fragment {

    SharedPreferences sharedpreferences;
DatabaseHandlerClass databaseHandlerClass;
TextView txt_fragment_Username,txt_fragment_email,txt_fragment_state,txt_fragment_city,txt_fragment_mobile;
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getUserData();
    }


    public void getUserData()
    {
        databaseHandlerClass = new DatabaseHandlerClass(getContext());
        sharedpreferences = getContext().getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
        getallId();
        String email = sharedpreferences.getString(Const.EMAIL,null);
        UserModel userModel = databaseHandlerClass.userprofile(email);
        txt_fragment_Username.setText(userModel.getName());
        txt_fragment_email.setText(userModel.getEmail());
        txt_fragment_state.setText(userModel.getState());
        txt_fragment_city.setText(userModel.getCity());
        txt_fragment_mobile.setText(userModel.getMobile());
    }
    public void getallId()
    {
        txt_fragment_Username = view.findViewById(R.id.txt_fragment_Username);
        txt_fragment_email = view.findViewById(R.id.txt_fragment_email);
        txt_fragment_state = view.findViewById(R.id.txt_fragment_state);
        txt_fragment_city = view.findViewById(R.id.txt_fragment_city);
        txt_fragment_mobile = view.findViewById(R.id.txt_fragment_mobile);
    }
}
