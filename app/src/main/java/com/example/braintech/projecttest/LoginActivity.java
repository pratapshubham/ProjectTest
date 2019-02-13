package com.example.braintech.projecttest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Model.Temp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText edt_username,edt_password;
    TextView txt_signup;
    Button btnLogin;
    String str_username,str_password;
    SharedPreferences sharedpreferences;



   /*Google SignIn Task below*/

    private GoogleSignInClient googleSignInClient;
    SignInButton btn_google_signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getId();

        googleSignIn();
        checkLogin();
        login();

        signUp();


    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Do you want to exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void signUp()
    {
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }

    private void googleSignIn()
    {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        btn_google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

    }


    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {

                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        onLoggedIn(account);

                    } catch (ApiException e)
                    {

                       e.printStackTrace();
                    }
                    break;
            }
    }


    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra(SignupActivity.GOOGLE_ACCOUNT, googleSignInAccount);
        signOut();
        startActivity(intent);
    }

    public void login()
    {
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
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplication(), "Failed to log in , please try again", Toast.LENGTH_SHORT).show();
                    }
                }
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
        btn_google_signin = (SignInButton)findViewById(R.id.btn_google_signIn);
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
        /*String password = sharedpreferences.getString(Const.PASSWORD,null);*/
        if(email !=null)
        {
            startActivity( new Intent(this,HomepageActivity.class));

        }
    }
}
