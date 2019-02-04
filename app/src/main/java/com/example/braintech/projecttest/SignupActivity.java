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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editext_name,editText_email,editText_phone;
    Button button_signup;
    Spinner spn_state,spn_city;
    String usr_name,usr_email,usr_phone;
    private ApiInterface apiInterface;
    ArrayAdapter<String> adapter;
    ArrayList<Datum> data ;
    AlertDialog.Builder builder;
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

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_name = editext_name.getText().toString();
                usr_email = editText_email.getText().toString();
                usr_phone = editText_phone.getText().toString();

                if (validation())
                {
                    builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle("SignUp Successfull");
                    builder.setMessage("Congrats ! Your SignUp Has Been Done");
                    builder.setCancelable(true);
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            finish();
                        }
                    });
                    builder.show();
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
        return true;
    }
    public void getAllid()
    {
        editext_name = (EditText) findViewById(R.id.edt_name);
        editText_email = (EditText)findViewById(R.id.edt_email);
        editText_phone = (EditText)findViewById(R.id.edt_phone);
        button_signup = (Button)findViewById(R.id.btn_Signup);
        spn_state = (Spinner) findViewById(R.id.spinner_state);
        spn_city = (Spinner)findViewById(R.id.spinner_city);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCountry = parent.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(),"You Selected : "+selectedCountry,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
