package com.sohos.totocafeandroid;

import java.util.Date;

/**
 * Created by dilkom71 on 13.11.2015.
 */
public class User {
    String name, surname , username, password;
    int age;

    public User(String name, String  surname, String username , String password , int age)
    {
        this.name  = name;
        this.surname  = surname;
        this.username  = username;
        this.password  = password;
        this.age  = age;

    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.name = "";

    }
}
