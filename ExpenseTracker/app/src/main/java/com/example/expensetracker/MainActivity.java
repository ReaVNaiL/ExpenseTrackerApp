package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.expensetracker.Views.Dashboard.DashboardViewListActivity;

public class MainActivity extends AppCompatActivity {
    private Button dashboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dashboardButton = findViewById(R.id.dashboard_button);

        dashboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DashboardViewListActivity.class);
            startActivity(intent);
        });
    }
}