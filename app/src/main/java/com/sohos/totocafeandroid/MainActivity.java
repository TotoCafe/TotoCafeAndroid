package com.sohos.totocafeandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    Intent i = new Intent(MainActivity.this,Login.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
            }
        }, 3000);
    }
}
