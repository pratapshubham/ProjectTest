package com.example.braintech.projecttest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Apiclient.ApiClient;
import com.example.braintech.projecttest.Apiclient.SignupApiClient;
import com.example.braintech.projecttest.Interface.ApiInterface;
import com.example.braintech.projecttest.Interface.SignupInterface;
import com.example.braintech.projecttest.Model.CountryListModel;
import com.example.braintech.projecttest.Model.CountryModel;
import com.example.braintech.projecttest.Model.Datum;
import com.example.braintech.projecttest.Model.HomeFragmentModel;
import com.example.braintech.projecttest.Model.StateModel;
import com.example.braintech.projecttest.Model.Temp;
import com.example.braintech.projecttest.Model.UserModel;
import com.example.braintech.projecttest.common.AdapterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editext_name,editText_email,editText_phone,edt_password;
    Button button_signup;
    Spinner spn_state,spn_city;
    TextView text_alreadyuser;
    String usr_name,usr_email,usr_phone,usr_password,usr_state,usr_city;
    private ApiInterface apiInterface;
    private SignupInterface signupInterface;
    ArrayList<Datum> data ;
    /*ArrayList<StateModel.Datum> stateList;*/
    DatabaseHandlerClass finalDatabaseHandler;
    SharedPreferences sharedpreferences;
    CountryModel country;
    StateModel stateModel;



    ArrayList<String> stateItem;



    //-------------------------

    ArrayList<CountryListModel> countryListModels;

    ArrayList<String> countryItem;

    ProgressDialog progressCountry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DatabaseHandlerClass databaseHandler = new DatabaseHandlerClass(getApplicationContext());
        Temp.setDatabaseHandler(databaseHandler);
        finalDatabaseHandler = Temp.getDatabaseHandler();

        getAllid();

       /*setCountryInitialAdapter();*/

        checkIn();

        getAllCountry();

        manageClickEvent();

        alreadyUser();

         spn_state.setOnItemSelectedListener(this);

         spn_city.setOnItemSelectedListener(this);
    }


   private void checkIn()
   {
       String email = sharedpreferences.getString(Const.EMAIL,null);
      /* String password = sharedpreferences.getString(Const.PASSWORD,null);*/
       if(email !=null)
       {
           startActivity( new Intent(this,HomepageActivity.class));

       }
   }



    private void alreadyUser()
    {
        text_alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });
    }



    private void getAllCountry(){

        countryListModels=new ArrayList<>();
        countryItem=new ArrayList<>();


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CountryModel> call = apiInterface.getCountryname();

        call.enqueue(new Callback<CountryModel>() {
            @Override
            public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {
                country =  response.body();
                setCountryAdapter();
            }

            @Override
            public void onFailure(Call<CountryModel> call, Throwable t) {

            }
        });

    }

    public void setCountryAdapter()
    {
        data = (ArrayList<Datum>) country.getData();
        CountryListModel countryModel;
        countryItem.add("Choose Country");
        for (int i = 0; i< data.size(); i++){
            countryModel=new CountryListModel();

            countryModel.setCountryName(data.get(i).getName());
            countryModel.setCountryId(data.get(i).getCountryId());

            countryListModels.add(countryModel);
            countryItem.add(data.get(i).getName());

        }

        // Spinner adapter

        spn_state.setAdapter(new ArrayAdapter<String>(SignupActivity.this,
                        R.layout.spinner_item_color, countryItem));


        // Spinner on item click listener
        spn_state
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        progressCountry = new ProgressDialog(SignupActivity.this);
                        progressCountry.setMessage("Loading...");
                        progressCountry.setCancelable(true);



                        String countryId=countryListModels.get(position).getCountryId();
                        countryListModels.get(position).getCountryId();
                        countryListModels.get(position).getCountryName();

                        System.out.println("Country Id------------------>"+countryListModels.get(position).getCountryId());
                        System.out.println("Country Name------------------>"+countryListModels.get(position).getCountryName());

                        if (spn_state.getSelectedItemPosition() == 0)
                        {
                           // Toast.makeText(getApplicationContext(),"Choose Country",Toast.LENGTH_SHORT).show();
                        }else
                            {
                        progressCountry.show();
                       getAllState(countryId);
                            }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    private void manageClickEvent(){

        button_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                usr_name = editext_name.getText().toString();
                usr_email = editText_email.getText().toString();
                usr_phone = editText_phone.getText().toString();
                usr_password = edt_password.getText().toString();
                usr_state = spn_state.getSelectedItem().toString();
                usr_city = spn_city.getSelectedItem().toString();
                if (validation())
                {
                    if (spn_state.getSelectedItemPosition() == 0 || spn_city.getSelectedItemPosition() == 0)
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Country or State",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        UserModel userModel = new UserModel(usr_name,usr_email,usr_state,usr_city,usr_phone,usr_password);
                        userModel.setName(usr_name);
                        userModel.setEmail(usr_email);
                        userModel.setState(usr_state);
                        userModel.setCity(usr_city);
                        userModel.setMobile(usr_phone);
                        userModel.setPassword(usr_password);
                        Boolean mail_check = finalDatabaseHandler.checkmail(usr_email);

                        if (mail_check == true)
                        {


                            int i = finalDatabaseHandler.insertdata(userModel);
                            if (i == 1)
                            {
                                editext_name.getText().clear();
                                editText_email.getText().clear();
                                editText_phone.getText().clear();
                                edt_password.getText().clear();
                                Toast.makeText(getApplication(), "Data Inserted", Toast.LENGTH_SHORT).show();
                                savePref();
                                checkIn();

                            }

                        }
                        else
                        {
                            Toast.makeText(getApplication(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });
    }


    public void savePref()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Const.EMAIL, usr_email);
        editor.putString(Const.PASSWORD, usr_password);
        editor.commit();
    }


    public Boolean validation()
    {
        if (usr_name.isEmpty())
        {
            editext_name.setError("Name Can't be Blank");
            editext_name.requestFocus();
            return false;
        }
        else if (usr_email.isEmpty())
        {
            editText_email.setError("Email Can't be Blank");
            editText_email.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(usr_email).matches())
        {
            editText_email.setError("Invalid Email");
            editText_email.requestFocus();
            return false;
        }
        else if(usr_phone.isEmpty())
        {
            editText_phone.setError("Enter Phone");
            editText_phone.requestFocus();
            return false;
        }
        else if(usr_password.isEmpty())
        {
            editText_phone.setError("Enter Password");
            editText_phone.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
       /*System.exit(0);*/
        finish();
    }

    public void getAllid()
    {
        editext_name = (EditText) findViewById(R.id.edt_name);
        editText_email = (EditText)findViewById(R.id.edt_email);
        editText_phone = (EditText)findViewById(R.id.edt_phone);
        edt_password = (EditText)findViewById(R.id.edt_signup_password);
        text_alreadyuser = (TextView)findViewById(R.id.txt_alreadyuser);
        button_signup = (Button)findViewById(R.id.btn_Signup);
        spn_state = (Spinner) findViewById(R.id.spinner_state);
        spn_city = (Spinner)findViewById(R.id.spinner_city);
        sharedpreferences = getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getAllState(String countryId)
    {
        int country_id = Integer.parseInt(countryId);
        stateItem=new ArrayList<>();
        signupInterface = SignupApiClient.getClient().create(SignupInterface.class);
        JSONObject jsonObject=new JSONObject();
        try
        {

            jsonObject.put("country_id",country_id-1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (jsonObject.toString()));

        Call<StateModel> getState = signupInterface.getAccess("application/json",
                "no-cache", body);

        getState.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                stateModel = response.body();

                System.out.println("State Size------------>"+response.body().getData().size());
                int totalState=response.body().getData().size();
                    stateItem.add("Choose State");
                for(int i=0;i<totalState;i++){
                    System.out.println("------------>"+stateModel.getData().get(i).getName());
                    stateItem.add(stateModel.getData().get(i).getName());

                }


                // Spinner adapter
                spn_city.setAdapter(new ArrayAdapter<String>(SignupActivity.this,
                                R.layout.spinner_item_color, stateItem));
                progressCountry.dismiss();

            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {

            }
        });

    }

}
