package com.zybooks.mobile2appinventory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText nameInput, descInput, countInput;
    Button saveChanges, cancelChanges;

    String id, name, desc, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        // EditTexts and Buttons
        nameInput = findViewById(R.id.editnameInput);
        descInput = findViewById(R.id.editdescriptionInput);
        countInput = findViewById(R.id.editCountInput);

        saveChanges = findViewById(R.id.save_changes_button);
        cancelChanges = findViewById(R.id.cancel_changes_button);

        // Copy information from clicked item to populate edit view
        getAndSetIntentData();

        // Push updates to database
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Push changes with Save Changes button
                InventoryDatabase db = new InventoryDatabase(UpdateActivity.this);

                name = nameInput.getText().toString().trim();
                desc = descInput.getText().toString().trim();
                count = countInput.getText().toString().trim();

                db.editItem(id, name, desc, count);
            }
        });

        // Go back to Recycler
        cancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, Recycler.class);
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