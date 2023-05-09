package com.example.pizzaq;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.Thread;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide unwanted GUI
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        // ---------------------------------------------------

        setContentView(R.layout.activity_main);

        ProgressBar loadingbar = findViewById(R.id.loading_bar);
        TextView progressTxt = findViewById(R.id.progressPercentage);
        progressTxt.setText("0%");

        Handler handler = new Handler();
        int delay = 45;

        Runnable runnable = new Runnable() {
            int curProgress = 0;
            @Override
            public void run() {
                if (curProgress < 100) {
                    curProgress += Math.round((Math.random() * 1) + 1);
                    progressTxt.setText(curProgress + "%");
                    loadingbar.setProgress(curProgress);
                    handler.postDelayed(this, delay);
                }
                else {
                    progressTxt.setText("100%");
                    startActivity(new Intent(StarterActivity.this, HomeActivity.class));
                }
            }
        };

        handler.postDelayed(runnable, delay);

        // (!) I need to remove the ability to go back to this activity (!)
    }
}