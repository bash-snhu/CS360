package com.zybooks.mobile2appinventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recycler extends AppCompatActivity {

    ArrayList<InventoryModel> inventoryModels = new ArrayList<>();
    ImageButton add_button, logout_button, notifButton, botNotificationButton;

    InventoryDatabase db;
    ArrayList<String> itemId, itemName, itemDesc, itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        db = new InventoryDatabase(Recycler.this);

        // If app has permission, send SMS updates on low stock
        if(getApplicationContext().checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            db.smsCheck();
        }

        // Toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Recycler item data
        itemName = new ArrayList<>();
        itemDesc = new ArrayList<>();
        itemCount = new ArrayList<>();

        // Populate data arrays from database
        dataToArrays();

        // Recycler
        RecyclerView recyclerView = findViewById(R.id.invRecyclerView);

        // Populate data models
        setUpInventoryModels();

        Inv_RecyclerViewAdapter adapter = new Inv_RecyclerViewAdapter(Recycler.this, this, inventoryModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Button for creating new entries
        // Starts add item activity
        add_button = findViewById(R.id.addButton);
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(Recycler.this, AddActivity.class);
            startActivity(intent);
        });

        // Logout Button
        logout_button = findViewById(R.id.settingsButton);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycler.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // SMS Settings Button
        notifButton = findViewById(R.id.notifButton);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recycler.this, SMS.class);
                startActivity(intent);
            }
        });

        // Button to trigger Notifications
        botNotificationButton = findViewById(R.id.notificationButton);
        botNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new InventoryDatabase(Recycler.this);
                db.smsCheck();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Refresh main activity
            recreate();
        }
    }

    void dataToArrays() {
        // Read all items for populating Recycler
        Cursor cursor = db.readItems();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                itemName.add(cursor.getString(0));
                itemDesc.add(cursor.getString(1));
                itemCount.add(cursor.getString(2));
            }
        }
    }

    private void setUpInventoryModels() {
        Cursor cursor = db.readItems();
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                inventoryModels.add(new InventoryModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }
        }
    }
}