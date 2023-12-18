package com.zybooks.mobile2appinventory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SMS extends AppCompatActivity {

    private  static final String MY_PERMISSIONS_REQUEST_SEND_SMS = Manifest.permission.SEND_SMS;
    Button requestPermission, backButton;
    private static final int PERMISSION_REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_notifications);

        // Request Permissions Button
        requestPermission = findViewById(R.id.enableButton);
        requestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If permissions are already available, toast. Otherwise, Request Permissions
                if(getApplicationContext().checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SMS.this, "Already Have Permission", Toast.LENGTH_SHORT).show();
                } else {
                    requestRunTimePermission();
                }
            }
        });

        // Back to Recycler
        backButton = findViewById(R.id.smsBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMS.this, Recycler.class);
                startActivity(intent);
            }
        });
    }

    private void requestRunTimePermission(){
        // Requests permission to SEND_SMS

        if(checkSelfPermission(MY_PERMISSIONS_REQUEST_SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(this, MY_PERMISSIONS_REQUEST_SEND_SMS)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app wants to send sms messages as low stock notifications.")
                    .setTitle("Permission Required")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        ActivityCompat.requestPermissions(this, new String[]{MY_PERMISSIONS_REQUEST_SEND_SMS}, PERMISSION_REQ_CODE);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{MY_PERMISSIONS_REQUEST_SEND_SMS}, PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(requestCode == PERMISSION_REQ_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}