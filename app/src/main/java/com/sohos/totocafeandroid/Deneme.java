package com.sohos.totocafeandroid;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Deneme extends AppCompatActivity implements ContactsListFragment.OnContactSelectedListener {

    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        if (savedInstanceState == null) {
//            if it's the first time, create and display the button fragment
            ButtonFragment buttonFragment = new ButtonFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, buttonFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            //activity recreated from saved state
        }

       /* if (getIntent().getExtras().getString("qr") != "") {
            TextView tv = (TextView) findViewById(R.id.tvMainQr);
            tv.setText(getIntent().getExtras().getString("qr"));

        }
        */

    }

    //called when button pressed, displays list of names in a new fragment
    public static void showContactsList() {
        ContactsListFragment contactListFragment = new ContactsListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contactListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onContactSelected(int position) {
        DisplayContactFragment displayContactFragment = new DisplayContactFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        displayContactFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, displayContactFragment)
                .addToBackStack(null)
                .commit();
    }

}
