package com.zybooks.mobile2appinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    TextView nameInput, descInput, countInput;
    Button editButton, backButton;

    String id, name, desc, count;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Context for sending to edit view
        context = getApplicationContext();

        // Instantiate toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        // Textviews and Buttons
        nameInput = findViewById(R.id.detailName);
        descInput = findViewById(R.id.detailDesc);
        countInput = findViewById(R.id.detailCount);

        editButton = findViewById(R.id.detailEditButton);
        backButton = findViewById(R.id.detailBackButton);

        // Copy information from clicked item to populate edit view
        getAndSetIntentData();

        // Button to enter edit mode
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send field data with intent to edit mode
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("desc", desc);
                intent.putExtra("count", count);

                // Start Detail View Activity
                startActivity(intent);
            }
        });

        // Go back to previous activity if cancelled
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, Recycler.class);
                startActivity(intent);
            }
        });
    }

    // Get info from clicked item and set existing text boxes
    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("desc") && getIntent().hasExtra("count")) {

            // Get data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            desc = getIntent().getStringExtra("desc");
            count = getIntent().getStringExtra("count");

            nameInput.setText(name);
            descInput.setText(desc);
            countInput.setText(count);

        }
    }
}