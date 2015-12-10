package com.sohos.totocafeandroid;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Table;

import org.json.JSONObject;

import java.util.List;

public class Deneme extends AppCompatActivity implements ContactsListFragment.OnContactSelectedListener {
    Context context;
    private static FragmentManager fragmentManager;
    public static double TableID = 0;
    public static double CompanyID = 0;
    public static double UserID =  1; //ŞİMDİLİK 1 , daha sonra doğrusu çekilecek.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        //checking qr code!
        checkQr();


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
    //QrCOdeReaderActivityden Bu sayfaya yönleniyoruz.
    //Önce qr stringimizi parse etmeye çalışcaz . Bakalım totoCafe mi yazıyo=?

    public void checkQr(){
        String qrValue = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            qrValue = bundle.getString("qr");

            String[] arr = qrValue.split("-");
            if(arr.length == 3){
                if(arr[0].equals("TotoCafe") && arr[1].matches("\\d+") && arr[2].matches("\\d+")){
                    CompanyID = Double.parseDouble(arr[1]);
                    TableID = Double.parseDouble(arr[2]);
                    Toast.makeText(this, arr[0] + " , " + arr[1] + " , " + arr[2], Toast.LENGTH_LONG).show();
                    new AsyncCheckAvailabilityOfTable().execute(TableID);

                }
                else{
                    Toast.makeText(this, "Thief!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Yanlış Kod okuttunuz!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected class AsyncCheckAvailabilityOfTable extends AsyncTask<Double, JSONObject, Boolean> {

        Double TableID = null;
        @Override
        protected Boolean doInBackground(Double... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CheckAvailabilityOfTable(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                TableID = params[0];
                Log.d("TID ChckAvailable: ", "TableID: " + TableID );
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckAvailabilityOfTable", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait... Check Availability of Table!",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            Log.d("Result on AsyncCheckAvailabilityOfTable : " , result.toString());
            //Check user validity
            if (result) { //Result -->
                Toast.makeText(context, "Table is Available. Now starting CheckTableControllerExist?",Toast.LENGTH_SHORT).show();
                new AsyncCheckTableControllerIsExist().execute(TableID);
                //Intent i = new Intent(LoginActivity.this,
                  //      HomeActivity.class);
                //i.putExtra("android_id",android_id);
                //startActivity(i);
            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Table FROZEN, Contact Manager!",Toast.LENGTH_SHORT).show();

                //  new AsyncAnonymousRegister().execute(android_id);
            }

        }

    }

    protected class AsyncCheckTableControllerIsExist extends AsyncTask<Double, JSONObject, Boolean> {
        //ASYNCTASK 1. PARAMETRE -- doInBackground
        //2.PARAMETRE -> onProgressUpdate
        //3.PARAMETRE -> onPostExecute ündür!
        double TableID=0;
        @Override
        protected Boolean doInBackground(Double... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CheckTableControllerIsExist(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                TableID =params[0];
                Log.d("TableID in AsyncCheckTableControllerIsExist", "Table id:" +TableID);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckTableControllerIsExist", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait... Check Table Controller Exist",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            Log.d("Result on AsyncCheckTableControllerIsExist : " , result.toString());

            //Check user validity
            if (result) { //Result --> Git Login Yap
                Toast.makeText(context, "Masa Boş, sırada InsertRequestTableViaQr işlemi var!",Toast.LENGTH_SHORT).show();
                //UserID daha sonra Eklenecek. Default = 1 şuan!
                Log.d("QRCODEbeforeInsertRequest", UserID + "," + CompanyID + "," + TableID);
                QrRequestTable qrRequestTable = new QrRequestTable(UserID,CompanyID,TableID);
                new AsyncInsertRequestTableViaQr().execute(qrRequestTable);

            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Masa Şuan Kullanımda!",Toast.LENGTH_SHORT).show();

            }

        }

    }

    protected class AsyncInsertRequestTableViaQr extends AsyncTask<QrRequestTable, Void, Void> {

        @Override
        protected Void doInBackground(QrRequestTable... params) {

            RestAPI api = new RestAPI();
            try {

                api.InsertRequestTableViaQr(params[0].getUserID(),params[0].getCompanyID(),params[0].getTableID());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncInsertRequestTableViaQr", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("Result on the INSERT REQUEST TABLE", result.toString());
            Toast.makeText(context , "Insert Request Table Via Qr - BAŞARILI!! " , Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            //startActivity(i);

            //ARTIK SERVER tarafından masa onayı beklendiği için son check Request işlemi kaldı
            QrRequestTable qrRequestTable = new QrRequestTable(UserID,CompanyID,TableID);
            new AsyncCheckRequestTableFlag().execute(qrRequestTable);
        }

    }

    //SON ASNYTASK CheckRequestTableFlag --
    //InsertRequestTableViaQR metotu ile flag = 0 olarak yollandı.
    //Sırada cevap bekliyoruz. CheckRequestTableFlag dan flag değişirse
    // bool olarak 'TRUE' döndürürse artık masa bizimdir!
    protected class AsyncCheckRequestTableFlag extends AsyncTask<QrRequestTable,Void,Boolean>{
        double TableID = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context,"Waiting for response from Server...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(QrRequestTable... params) {
            RestAPI api = new RestAPI();
            boolean authentication = false;
            try {
                do{
                    // Call the User Authentication Method in API
                    JSONObject jsonObj = api.CheckRequestTableFlag(params[0].getUserID(),params[0].getCompanyID(),params[0].getTableID());

                    //Parse the JSON Object to boolean
                    JSONParser parser = new JSONParser();
                    authentication = parser.parseUserAuth(jsonObj);
                    TableID = params[0].getTableID();
                    Thread.sleep(2000);
                }while(authentication);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckRequestTableFlag", e.getMessage());

            }
            return authentication;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("SONUC ", result.toString());
           if(result){ // result is true! Response is receiving from server!!
               //Intent i = new Intent(context, HomeActivity.class);
               //startActivity(i);

           }
        }
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
