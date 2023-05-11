package com.example.pizzaq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzaq.ignore.Personal;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SEND_SMS = 01010011;
    private String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        this.hideUnwantedGUI();

        order = getIntent().getStringExtra("order");

        TextView orderListView = findViewById(R.id.orderListView);
        if (order.length() > 0) orderListView.setText(order);

        Context context = this;

        findViewById(R.id.orderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndSendMessage(context);

                Log.d("some", "Message sent");

                Intent intent = new Intent(OrderActivity.this, ValidationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkPermissionAndSendMessage(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_SEND_SMS);
        }
        else {
            sendSms();
        }
    }

    private void sendSms() {
        try {
            SmsManager.getDefault().sendTextMessage(
                    Personal.PERSONAL_NUM,
                    null,
                    order,
                    null,
                    null
            );

            Toast.makeText(this, "Your order is sent successfully!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Order failed to send.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            }
            else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void hideUnwantedGUI() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }
}