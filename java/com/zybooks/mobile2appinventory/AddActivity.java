package com.zybooks.mobile2appinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

public class AddActivity extends AppCompatActivity {

    EditText nameInput, descriptionInput, stockInput;
    Button addButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // Add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        // Inputs and Buttons
        nameInput = findViewById(R.id.nameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        stockInput = findViewById(R.id.stockInput);

        addButton = findViewById(R.id.addNewButton);
        cancelButton = findViewById(R.id.cancelNewButton);

        // Adds item to database on click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryDatabase db = new InventoryDatabase(AddActivity.this);
                db.addItem(
                        nameInput.getText().toString().trim(),
                        descriptionInput.getText().toString().trim(),
                        Integer.valueOf(stockInput.getText().toString().trim()));
            }}
        );

        // Cancel Button - Starts Recycler activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, Recycler.class);
                startActivity(intent);
            }
        });
    }
}