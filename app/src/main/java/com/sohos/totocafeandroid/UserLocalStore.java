package com.sohos.totocafeandroid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by dilkom71 on 13.11.2015.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }//end Contructor

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("surname", user.surname);
        spEditor.putString("username", user.surname);
        spEditor.putString("password", user.password);
        spEditor.putInt("age", user.age);

        spEditor.commit();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String surname = userLocalDatabase.getString("surname", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        int age = userLocalDatabase.getInt("age", -1 );
        User storedUser = new User(name, surname,username,password,age);

        return  storedUser;
    }

    public  void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn",false)){
            return  true;
        }   else{
            return false;
        }
    }

    public void clearUserDate(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}//end class UserLocalStore
