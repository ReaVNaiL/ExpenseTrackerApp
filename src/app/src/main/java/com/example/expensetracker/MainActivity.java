package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.expensetracker.Views.Dashboard.DashboardViewListActivity;
import com.example.expensetracker.Views.Settings.SettingsEditActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dashboardButton = findViewById(R.id.dashboard_button);
        dashboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DashboardViewListActivity.class);
            startActivity(intent);
        });

        Button settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsEditActivity.class);
            startActivity(intent);
        });

        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

    }
}