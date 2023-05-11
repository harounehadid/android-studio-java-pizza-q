package com.example.pizzaq;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Pizza> pizzasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.hideUnwantedGUI();

        setContentView(R.layout.activity_home);

        // Display pizza cards
        pizzasList = this.connectAndRetrieveData();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        MyAdapter adapter = new MyAdapter(pizzasList, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Cart button
        findViewById(R.id.cartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayOrderPage();
            }
        });
    }

    private void displayOrderPage() {
        String orderList = "";
        int index = 1;

        for (Pizza pizza : pizzasList) {
            if (pizza.getAmount() > 0) {
                orderList += Integer.toString(index) + ") " + pizza.getName() + " X " + Integer.toString(pizza.getAmount()) + "\n";
                index++;
            }
        }

        Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
        intent.putExtra("order", orderList);
        startActivity(intent);
    }

    private void hideUnwantedGUI() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }

    private ArrayList<Pizza> connectAndRetrieveData() {
        ArrayList<Pizza> pizzasList = new ArrayList<>();

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
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_NAME)
            );
            int price = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_PRICE)
            );
            int calories = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_CALORIES)
            );
            String ingredients = cursor.getString(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_INGREDIENTS)
            );
            String img_id = cursor.getString(
                    cursor.getColumnIndexOrThrow(PizzaContract.COLUMN_IMG_ID)
            );

            pizzasList.add(
                    new Pizza(id, name, price, calories, ingredients, img_id)
            );
        }

        cursor.close();
        db.close();

        return pizzasList;
    }
}