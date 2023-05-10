package com.example.pizzaq;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.hideUnwantedGUI();

        setContentView(R.layout.activity_home);

        this.connectAndRetrieveData();
    }

    private void hideUnwantedGUI() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }

    private void connectAndRetrieveData() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createDatabase();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Listing the columns to retrieve
        final String[] selectedCols = {
                PizzaContract.COLUMN_ID,
                PizzaContract.COLUMN_NAME,
                PizzaContract.COLUMN_PRICE,
                PizzaContract.COLUMN_CALORIES,
                PizzaContract.COLUMN_INGREDIENTS,
                PizzaContract.COLUMN_IMG_ID
        };

        Cursor cursor = db.query(
                PizzaContract.TABLE_NAME,
                selectedCols,
                null,
                null,
                null,
                null,
                PizzaContract.COLUMN_ID + " ASC"
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            Log.d("some", name);
        }

        cursor.close();
        db.close();
    }
}