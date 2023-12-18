package com.zybooks.mobile2appinventory;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class InventoryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private  static final String MY_PERMISSIONS_REQUEST_SEND_SMS = Manifest.permission.SEND_SMS;
    private static final int VERSION = 1;
    private Context context;

    private static final class InventoryTable {
        private static final String TABLE = "inventory";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_DESCRIPTION = "description";
        private static final String COL_COUNT = "count";
    }

    public InventoryDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Initialize Database
        String query = "create table " + InventoryTable.TABLE + " (" +
                InventoryTable.COL_ID + " integer primary key autoincrement, " +
                InventoryTable.COL_NAME + " text, " +
                InventoryTable.COL_DESCRIPTION + " text, " +
                InventoryTable.COL_COUNT + " integer);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + InventoryTable.TABLE);
        onCreate(db);
    }

    // Add single item to database
    void addItem(String name, String description, int count) {
        // Add single item to Database

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(InventoryTable.COL_NAME, name);
        cv.put(InventoryTable.COL_DESCRIPTION, description);
        cv.put(InventoryTable.COL_COUNT, count);

        long result = db.insert(InventoryTable.TABLE, null, cv);

        if(result == -1) {
            Toast.makeText(context, "Failed to Add Item!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item Successfully Added!", Toast.LENGTH_SHORT).show();
        }
    }

    // Read all items from database
    Cursor readItems() {
        // Read all items from database and return cursor

        String query = "SELECT * FROM " + InventoryTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    };

    void editItem(String row_id, String name, String description, String count) {
        // Push updates to database

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(InventoryTable.COL_NAME, name);
        cv.put(InventoryTable.COL_DESCRIPTION, description);
        cv.put(InventoryTable.COL_COUNT, count);

        long result = db.update(InventoryTable.TABLE, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to Update Item!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Item!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteSingleItem(String row_id) {
        // Delete single item from database

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(InventoryTable.TABLE, "_id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Failed to Delete Item!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Item!", Toast.LENGTH_SHORT).show();
        }
    }

    void smsCheck() {
        // Checks for items below 10 and sends an SMS message to warn the user

        String query = "SELECT * FROM " + InventoryTable.TABLE + " WHERE count < 10";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        // Try to iterate through cursor
        try {
            while (cursor.moveToNext()) {
                // Generate SMS Message if SMS permission has been granted based on items that
                // have less than 10 units in stock

                if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    String message = "WARNING: Item named " + cursor.getString(1) +
                            " has low stock (" + cursor.getString(3) + " units)!";

                    // Android emulator phone number
                    String srcNumber = "15551234567";

                    // Send message
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(srcNumber, null, message, null, null);
                }
            }
        } finally {
            cursor.close();
        }
    }
}
