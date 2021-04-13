package com.example.breaking_bad.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breaking_bad.Helper.LoginDataBaseAdapter;
import com.example.breaking_bad.Helper.SessionManager;
import com.example.breaking_bad.R;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Boolean stored = false;
    SessionManager session;
    Boolean login = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */


        if(session.isLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }





// create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

// Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

// Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// TODO Auto-generated method stub

/// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(), SignUPActivity.class);
                startActivity(intentSignUP);
                finish();
            }
        });
    }
    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");

// get the Refferences of views
        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

// Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

// get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

// fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
                Log.d("storedPassword",storedPassword);

// check if the Stored password matches with Password entered by user
                if(password.equals(storedPassword))
                {
                    Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();


                    login = true;
                    session.updateBoolean();
                    Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter.close();
    }
}