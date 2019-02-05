package com.example.braintech.projecttest;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.braintech.projecttest.Model.Temp;
import com.example.braintech.projecttest.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editext_name,editText_email,editText_phone,edt_password;
    Button button_signup;
    Spinner spn_state,spn_city;
    String usr_name,usr_email,usr_phone,usr_password,usr_state,usr_city;
    private ApiInterface apiInterface;
    ArrayAdapter<String> adapter;
    ArrayList<Datum> data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("SignUp Form ");

        getAllid();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CountryModel> call = apiInterface.getCountryname();

      call.enqueue(new Callback<CountryModel>() {
          @Override
          public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {
              CountryModel country =  response.body();
              data = (ArrayList<Datum>) country.getData();
              Log.d("List size------>",data.size()+"");
              String[] items = new String[data.size()];
              for(int i=0;i<data.size();i++)
              {
                  items[i] = data.get(i).getName();
              }
              adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, items);
              spn_state.setAdapter(adapter);
          }

          @Override
          public void onFailure(Call<CountryModel> call, Throwable t) {

          }
      });


        DatabaseHandlerClass databaseHandler = new DatabaseHandlerClass(getApplicationContext());
        Temp.setDatabaseHandler(databaseHandler);
        databaseHandler = Temp.getDatabaseHandler();

        final DatabaseHandlerClass finalDatabaseHandler = databaseHandler;


        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_name = editext_name.getText().toString();
                usr_email = editText_email.getText().toString();
                usr_phone = editText_phone.getText().toString();
                usr_password = edt_password.getText().toString();
                usr_state = spn_state.getSelectedItem().toString();
                usr_city = spn_city.getSelectedItem().toString();
                if (validation())
                {
                    UserModel userModel = new UserModel(usr_name,usr_email,usr_state,usr_city,usr_phone,usr_password);
                    userModel.setName(usr_name);
                    userModel.setEmail(usr_email);
                    userModel.setState(usr_state);
                    userModel.setCity(usr_city);
                    userModel.setMobile(usr_phone);
                    userModel.setPassword(usr_password);
                    Boolean mail_check = finalDatabaseHandler.checkmail(usr_email);
                    if (mail_check == true) {
                        int i = finalDatabaseHandler.insertdata(userModel);
                        if (i == 1) {
                            editext_name.getText().clear();
                            editText_email.getText().clear();
                            editText_phone.getText().clear();
                            edt_password.getText().clear();
                            Toast.makeText(getApplication(), "Data Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplication(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

         spn_state.setOnItemSelectedListener(this);
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
    public void getAllid()
    {
        editext_name = (EditText) findViewById(R.id.edt_name);
        editText_email = (EditText)findViewById(R.id.edt_email);
        editText_phone = (EditText)findViewById(R.id.edt_phone);
        edt_password = (EditText)findViewById(R.id.edt_signup_password);
        button_signup = (Button)findViewById(R.id.btn_Signup);
        spn_state = (Spinner) findViewById(R.id.spinner_state);
        spn_city = (Spinner)findViewById(R.id.spinner_city);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCountry = parent.getSelectedItem().toString();
        //Toast.makeText(getApplicationContext(),"You Selected : "+selectedCountry,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
