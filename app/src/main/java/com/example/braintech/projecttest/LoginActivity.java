package com.example.braintech.projecttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText edt_username,edt_password;
    TextView txt_signup;
    Button btnLogin;
    String str_username,str_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Form");

        getId();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = edt_username.getText().toString();
                str_password = edt_password.getText().toString();
                if (Validate())
                {

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

    public void getId()
    {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btnLogin = (Button)findViewById(R.id.btn_Login);
        txt_signup = (TextView)findViewById(R.id.txt_SignUp);
    }

    public Boolean Validate()
    {
        if (str_username.isEmpty())
        {
            edt_username.setError("Username Can't be Blank");
            edt_username.requestFocus();
            return false;
        }
        else if (str_password.isEmpty())
        {
            edt_password.setError("Enter Password");
            edt_password.requestFocus();
            return false;
        }
        return true;
    }
}
