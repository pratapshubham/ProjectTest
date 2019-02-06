package com.example.braintech.projecttest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Model.Temp;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText edt_username,edt_password;
    TextView txt_signup;
    Button btnLogin;
    String str_username,str_password;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Form");
        getId();
        checkLogin();
        DatabaseHandlerClass databaseHandler = new DatabaseHandlerClass(getApplicationContext());
        Temp.setDatabaseHandler(databaseHandler);
        databaseHandler = Temp.getDatabaseHandler();

        final DatabaseHandlerClass finalDatabaseHandler = databaseHandler;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = edt_username.getText().toString();
                str_password = edt_password.getText().toString();
                if (Validate())
                {
                    Boolean authenticateuser = finalDatabaseHandler.AuthenticateUser(str_username,str_password);

                    if (authenticateuser == true)
                    {
                    startActivity(new Intent(LoginActivity.this,HomepageActivity.class));
                    edt_username.getText().clear();
                    edt_password.getText().clear();
                    savePref();
                    }
                    else
                    {
                        Toast.makeText(getApplication(), "Failed to log in , please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });


    }
    public void  savePref()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Const.EMAIL, str_username);
        editor.putString(Const.PASSWORD, str_password);
        editor.commit();
    }
    public void getId()
    {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btnLogin = (Button)findViewById(R.id.btn_Login);
        txt_signup = (TextView)findViewById(R.id.txt_SignUp);
        sharedpreferences = getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public Boolean Validate()
    {
        if (str_username.isEmpty())
        {
            edt_username.setError("Username Can't be Blank");
            edt_username.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(str_username).matches())
        {
            edt_username.setError("Invalid Email");
            edt_username.requestFocus();
        }
        else if (str_password.isEmpty())
        {
            edt_password.setError("Enter Password");
            edt_password.requestFocus();
            return false;
        }
        return true;
    }
    private void checkLogin()
    {
        String email = sharedpreferences.getString(Const.EMAIL,null);
        String password = sharedpreferences.getString(Const.PASSWORD,null);
        if(email !=null)
        {
            startActivity( new Intent(this,HomepageActivity.class));

        }
    }
}
