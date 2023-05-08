package com.example.pizzaq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar loadingbar = findViewById(R.id.loading_bar);

        Handler handler = new Handler();
        int delay = 45;

        Runnable runnable = new Runnable() {
            int curProgress = 0;
            @Override
            public void run() {
                curProgress += Math.round((Math.random() * 1) + 1);
                loadingbar.setProgress(curProgress);
                handler.postDelayed(this, delay);
            }
        };

        handler.postDelayed(runnable, delay);
    }
}