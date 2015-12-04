package com.sohos.totocafeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

        import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
        import android.widget.Toast;
import android.content.Context;
import android.provider.Settings.Secure;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin,btnAnonymousLogin,btnFacebookLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize  the layout components
        context=this;
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnAnonymousLogin = (Button) findViewById(R.id.btn_Anonymous);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();

                // Execute the AsyncLogin class
                new AsyncLogin().execute(email,password);

            }
        });

        btnAnonymousLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String android_id = Secure.getString(getApplication().getContentResolver(),
                        Secure.ANDROID_ID);

                new AsyncAnonymousLogin().execute(android_id);
            }
        });


    }
    // NORMAL LOGİN
    protected class AsyncLogin extends AsyncTask<String, JSONObject, Boolean> {

        String userName=null;
        @Override
        protected Boolean doInBackground(String... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.UserAuthentication(params[0],
                        params[1]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);

                userName=params[0];
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLogin", e.getMessage());
            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub

            //Check user validity
            if (result) {
                Intent i = new Intent(LoginActivity.this,
                        HomeActivity.class);
                i.putExtra("username",userName);
                startActivity(i);
            }
            else
            {
                Toast.makeText(context, "Not valid username/password ",Toast.LENGTH_SHORT).show();
            }

        }

    }

    //ANONYMOUS LOGİN
    protected class AsyncAnonymousLogin extends AsyncTask<String, JSONObject, Boolean> {

        String android_id=null;
        @Override
        protected Boolean doInBackground(String... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.AnonymousAuthentication(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                android_id =params[0];
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLogin", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub

            //Check user validity
            if (result) { //Result --> Git Login Yap
                Toast.makeText(context, "Anonymous Login...",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(LoginActivity.this,
                        HomeActivity.class);
                i.putExtra("android_id",android_id);
                startActivity(i);
            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Anonymous Registration...",Toast.LENGTH_SHORT).show();

                new AsyncAnonymousRegister().execute(android_id);
            }

        }

    }

    //ANONYMOUS REGİSTER
    protected class AsyncAnonymousRegister extends AsyncTask<String, Void, Void> {

        String android_id=null;
        @Override
        protected Void doInBackground(String... params) {

            RestAPI api = new RestAPI();

            try {

                api.CreateNewAnonymous(params[0]);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncAnonymousLogin", e.getMessage());

            }
            return  null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }

    }





}