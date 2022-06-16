package com.example.news_app_demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private final Context mContext;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "NewsApp_demo.db";
    // Table Name
    public static final String TABLE_USERS="users";
    //Column Users
    public static final String USERS_ID = "user_id";
    public static final String USER_NAME="user_name";
    public static final String USER_PHONE="user_phone";
    public static final String USER_PASSWORD="user_password";


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    // Table Create Statement
    //Users
    private static  final String CREATE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT,"
            + USER_PHONE + " TEXT,"
            + USER_PASSWORD + " TEXT"
            + ")";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL("INSERT INTO " + TABLE_USERS + "(user_id, user_name,user_phone,user_password) VALUES(1,'Admin','+998993577505','12345')");

       }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

}