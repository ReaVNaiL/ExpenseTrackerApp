package com.example.expensetracker.Views.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.expensetracker.Data.DataHandler;
import com.example.expensetracker.Dialogs.ErrorDialog;
import com.example.expensetracker.MainActivity;
import com.example.expensetracker.Models.Category;
import com.example.expensetracker.R;
import com.example.expensetracker.Views.Dashboard.DashboardViewListActivity;

import java.util.List;

public class SettingsEditActivity extends AppCompatActivity {
    private List<Category> categories;

    private EditText restaurantBudget;
    private EditText groceriesBudget;
    private EditText travelBudget;
    private EditText entertainmentBudget;
    private EditText healthBudget;
    private EditText otherBudget;
    private DataHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit);

        dbHandler = new DataHandler(this);

        // Get settings form
        getContentSettings();

        // Set the settings
        setBudgetSettings();

        Button backButton = findViewById(R.id.main_back);
        backButton.setOnClickListener(v -> {
            finish();
        });

        Button saveButton = findViewById(R.id.main_save);
        saveButton.setOnClickListener(v -> {
            saveSettings();
        });
    }

    private void getContentSettings() {
        restaurantBudget = findViewById(R.id.restaurant_budget);
        groceriesBudget = findViewById(R.id.groceries_budget);
        travelBudget = findViewById(R.id.travel_budget);
        entertainmentBudget = findViewById(R.id.entertainment_budget);
        healthBudget = findViewById(R.id.health_budget);
        otherBudget = findViewById(R.id.other_budget);
    }

    public void saveSettings() {
        dbHandler.open();

        // Get the settings from the layout
        String restaurantBudgetString = restaurantBudget.getText().toString();
        String groceriesBudgetString = groceriesBudget.getText().toString();
        String travelBudgetString = travelBudget.getText().toString();
        String entertainmentBudgetString = entertainmentBudget.getText().toString();
        String healthBudgetString = healthBudget.getText().toString();
        String otherBudgetString = otherBudget.getText().toString();

        // Update the settings in the database
        dbHandler.updateCategory("Restaurants", Double.parseDouble(restaurantBudgetString));
        dbHandler.updateCategory("Groceries", Double.parseDouble(groceriesBudgetString));
        dbHandler.updateCategory("Travel", Double.parseDouble(travelBudgetString));
        dbHandler.updateCategory("Entertainment", Double.parseDouble(entertainmentBudgetString));
        dbHandler.updateCategory("Health", Double.parseDouble(healthBudgetString));
        dbHandler.updateCategory("Other", Double.parseDouble(otherBudgetString));

        dbHandler.close();

        ErrorDialog errorDialog = new ErrorDialog(this, "Confirmation", "Settings saved!");
        errorDialog.showAcknowledgment(this::backToMenu);
    }

    public void backToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setBudgetSettings() {
        dbHandler.open();
        // Get the settings from the database
        categories = dbHandler.getAllCategories();

        dbHandler.close();

        // Set the settings in the layout
        for (Category category : categories) {
            switch (category.getName()) {
                case "Restaurants":
                    restaurantBudget.setText(String.valueOf(category.getBudget()));
                    break;
                case "Groceries":
                    groceriesBudget.setText(String.valueOf(category.getBudget()));
                    break;
                case "Travel":
                    travelBudget.setText(String.valueOf(category.getBudget()));
                    break;
                case "Entertainment":
                    entertainmentBudget.setText(String.valueOf(category.getBudget()));
                    break;
                case "Health":
                    healthBudget.setText(String.valueOf(category.getBudget()));
                    break;
                case "Other":
                    otherBudget.setText(String.valueOf(category.getBudget()));
                    break;
            }
        }
    }
}