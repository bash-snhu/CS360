package com.zybooks.mobile2appinventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Get username and password fields
        username = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);

        // Login Button
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from username and password
                String stringusername, stringpassword;
                stringusername = username.getText().toString().trim();
                stringpassword = password.getText().toString().trim();

                // If login matches, start Recycler, otherwise Register the user
                LogInDatabase db = new LogInDatabase(MainActivity.this);
                if(!db.Exists(stringusername, stringpassword)) {
                    db.addItem(stringusername, stringpassword);
                } else {
                    Intent intent = new Intent(MainActivity.this, Recycler.class);
                    startActivity(intent);
                }
            }
        });
    }
}



