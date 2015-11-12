package com.sohos.totocafeandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnLogout;
    EditText etName, etSurname ,etUsername ;


//  private Handler mHandler = new Handler();
    //Intent i = new Intent(MainActivity.this,Login.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   mHandler.postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
            }
        }, 3000);
    */
        etName =(EditText) findViewById(R.id.etName);
        etSurname =(EditText) findViewById(R.id.etSurname);
        etUsername =(EditText) findViewById(R.id.etUsername);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

}
