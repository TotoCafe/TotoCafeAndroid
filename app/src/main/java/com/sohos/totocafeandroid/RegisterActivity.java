package com.sohos.totocafeandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etSurname, etEmail, etPassword;
    Button btnRegister;
    RadioGroup radioGroup;
    RadioButton rbMale,rbFemale;

    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();

        etName =(EditText) findViewById(R.id.et_name);
        etSurname = (EditText) findViewById(R.id.et_surname);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupGender);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);

        btnRegister=(Button) findViewById(R.id.btn_Register);


        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name, surname, email, password;
                double gender = 0;

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == rbMale.getId()) {
                    gender = 1;
                } else if (selectedId == rbFemale.getId()) {
                    gender = 2;
                }


                name = etName.getText().toString();
                surname = etSurname.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                //String birthDate = day_x+"\\"+month_x+"\\"+year_x;
                String birthDate = day_x + "."+ month_x + "." + year_x;
                Log.d("My date : ", birthDate.toString());
                UserDetailsTable userDetail = new UserDetailsTable(name,
                        surname, email, password, birthDate.toString(), gender);

                new AsyncCreateUser().execute(userDetail);

            }
        });

    }


    public void showDialogOnButtonClick(){
        Button btn = (Button) findViewById(R.id.btnDate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, dpickerListener,year_x,month_x,day_x);
        }
        return  null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            //Toast.makeText(RegisterActivity.this, year_x+"/"+month_x+"/"+day_x,Toast.LENGTH_LONG).show();

        }
    };



    protected class AsyncCreateUser extends
            AsyncTask<UserDetailsTable, Void, Void> {

        @Override
        protected Void doInBackground(UserDetailsTable... params) {

            RestAPI api = new RestAPI();
            try {

                api.CreateNewUser(params[0].getName(), params[0].getSurname(), params[0].getEmail(),
                        params[0].getPassword(), params[0].getBirthDate(),params[0].getGender());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(RegisterActivity.this , "Registration is success! " , Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(i);
        }

    }



}
