package com.zybooks.mobile2appinventory;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class LogInDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "logins.db";
    private static final int VERSION = 1;
    private Context context;

    private static final class LogInTable {
        private static final String TABLE = "logins";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    public LogInDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create login table

        String query = "create table " + LogInTable.TABLE + " (" +
                LogInTable.COL_ID + " integer primary key autoincrement, " +
                LogInTable.COL_USERNAME + " text, " +
                LogInTable.COL_PASSWORD + " text);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LogInTable.TABLE);
        onCreate(db);
    }

    // Add single item to database
    void addItem(String username, String password) {
        // Add single user to database

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(LogInTable.COL_USERNAME, username);
        cv.put(LogInTable.COL_PASSWORD, password);

        long result = db.insert(LogInTable.TABLE, null, cv);

        if(result == -1) {
            Toast.makeText(context, "Failed to Register.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "You are now Registered. You may now Log in.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean Exists(String username, String password) {

        // Check if user and password exists in database, return boolean if true or false

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + LogInTable.TABLE + " WHERE username == '" + username + "' AND password == '" + password + "'";

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        // If query exists return true or false
        if((cursor != null) && (cursor.getCount() > 0)) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
