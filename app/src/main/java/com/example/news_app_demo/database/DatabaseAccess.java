package com.example.news_app_demo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseAccess {

    private static DatabaseAccess instance;
    private SQLiteDatabase database;
    private final SQLiteOpenHelper openHelper;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }
















    public ArrayList<HashMap<String, String>> checkUser(String phone, String password) {
        ArrayList<HashMap<String, String>> userInfo = new ArrayList<>();


        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE user_phone='" + phone + "' AND user_password='" + password + "'", null);


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();


                map.put(DatabaseOpenHelper.USER_NAME, cursor.getString(1));



                userInfo.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return userInfo;
    }

    public boolean addUser(String name, String phone, String password) {


        Cursor result = database.rawQuery("SELECT * FROM users WHERE user_phone='" + phone + "'", null);
        if (result.getCount() >= 1) {

            Log.d("data", "Already added");
            return false;

        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseOpenHelper.USER_NAME, name);
            values.put(DatabaseOpenHelper.USER_PHONE, phone);
            values.put(DatabaseOpenHelper.USER_PASSWORD, password);


            long check = database.insert("users", null, values);

            result.close();
            database.close();

            //if data insert success, its return 1, if failed return -1
            return check != -1;
        }
    }

    public boolean updateUser(String id, String name, String phone, String password ) {

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.USER_NAME, name);
        values.put(DatabaseOpenHelper.USER_PHONE, phone);
        values.put(DatabaseOpenHelper.USER_PASSWORD, password);



        long check = database.update("users", values, "user_id=?", new String[]{id});
        database.close();

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }

    }

    //get user data
    public ArrayList<HashMap<String, String>> getUsers() {
        ArrayList<HashMap<String, String>> users = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM users ORDER BY user_id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(DatabaseOpenHelper.USERS_ID, cursor.getString(0));
                map.put(DatabaseOpenHelper.USER_NAME, cursor.getString(1));
                map.put(DatabaseOpenHelper.USER_PHONE, cursor.getString(2));
                map.put(DatabaseOpenHelper.USER_PASSWORD, cursor.getString(3));
                users.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return users;
    }




    //search user data
    public ArrayList<HashMap<String, String>> searchUser(String s) {
        ArrayList<HashMap<String, String>> userData = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE user_name LIKE '%" + s + "%' ORDER BY user_id DESC ", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(DatabaseOpenHelper.USERS_ID, cursor.getString(0));
                map.put(DatabaseOpenHelper.USER_NAME, cursor.getString(1));
                map.put(DatabaseOpenHelper.USER_PHONE, cursor.getString(2));
                map.put(DatabaseOpenHelper.USER_PASSWORD, cursor.getString(3));
                userData.add(map);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return userData;
    }


}