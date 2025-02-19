package com.example.myapplication.DBUtil;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Entity.Users;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "myDB";

    private static final int DB_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userQuery = " create table users( id integer PRIMARY KEY AUTOINCREMENT," +
                " name text, email text, pass text, dob text)";

        db.execSQL(userQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", user.username);
        values.put("EMAIL", user.email);
        values.put("PASS", user.password);
        values.put("DOB", user.dob);

        return db.insert("USERS", null, values);
    }

    public Users getUserByUserID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("USERS", null, "id" + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            Users user = new Users(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("PASS")),
                    cursor.getString(cursor.getColumnIndex("DOB"))
            );
            cursor.close();
            return user;
        } else {
            return null; // user not found
        }
    }

    // Get all users from the database
    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("USERS", null, null, null, null, null, null);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from USERS ", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Users user = new Users(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                userList.add(user);
            }
            cursor.close();
        }

        return userList;
    }

    // Update user details
    public int updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.username);
        values.put("email", user.email);
        values.put("pass", user.password);
        values.put("dob", user.dob);

        return db.update("USERS", values, "id" + " = ?", new String[]{String.valueOf(user.id)});
    }

    // Delete user by username
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("USERS", "id" + " = ?", new String[]{String.valueOf(id)});
    }


    public int loginUser(String userName, String password){
        String[] arr = new String[2];
        arr[0] = userName;
        arr[1] = password;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(" select * from users where name=? and pass=? ", arr);
        if (c.moveToFirst()){
            return 1;
        }
        return 0;
    }




}
