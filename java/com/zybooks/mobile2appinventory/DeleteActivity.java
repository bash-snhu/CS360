package com.zybooks.mobile2appinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DeleteActivity extends AppCompatActivity {

    TextView nameView, descView, countView;
    Button confirmDelete, cancelDelete;

    String id, name, desc, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Instantiate toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.delToolbar);
        setSupportActionBar(toolbar);

        // Textviews and Buttons
        nameView = findViewById(R.id.delNameView);
        descView = findViewById(R.id.delDescView);
        countView = findViewById(R.id.delCountView);

        confirmDelete = findViewById(R.id.confirmDeleteButton);
        cancelDelete = findViewById(R.id.cancelDeleteButton);

        // Copy information from clicked item to populate edit view
        getAndSetIntentData();

        // Deletes item from database and returns to main activity
        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Push changes with Save Changes button
                InventoryDatabase db = new InventoryDatabase(DeleteActivity.this);

                // Delete item from Database
                db.deleteSingleItem(id);

                // Restart Recycler View
                Intent intent = new Intent(DeleteActivity.this, Recycler.class);
                startActivity(intent);
            }
        });

        // Go back to Recycler activity if cancelled
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteActivity.this, Recycler.class);
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

            nameView.setText(name);
            descView.setText(desc);
            countView.setText(count);
        }
    }
}