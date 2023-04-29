package com.example.expensetracker.Views.TransactionDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.expensetracker.Data.DataHandler;
import com.example.expensetracker.Dialogs.ErrorDialog;
import com.example.expensetracker.Models.Expense;
import com.example.expensetracker.R;
import com.example.expensetracker.Views.Dashboard.DashboardViewListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TransactionDetailsActivity extends AppCompatActivity {
    private DataHandler dbHandler;
    private int transactionId;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        // Get the transaction id from the intent
        transactionId = getIntent().getIntExtra("expenseId", 0);

        // Set up database connection
        dbHandler = new DataHandler(this);

        // Set up All Transaction details
        getTransactionDetails();
        setTransactionDetails();

        // Set up delete button
        FloatingActionButton deleteButton = findViewById(R.id.delete_transaction_button);
        deleteButton.setOnClickListener(v -> {
            deleteTransaction();
        });

        // Set up back button
        Button backButton = findViewById(R.id.back_dash_button);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void getTransactionDetails() {
        dbHandler.open();

        // Get the transaction from the database
        expense = dbHandler.getTransactionDetails(transactionId);

        dbHandler.close();
    }

    private void setTransactionDetails() {
        // Set the transaction details
        TextView transactionName = findViewById(R.id.transaction_name_label);
        transactionName.setText(expense.getName());

        TextView transactionAmount = findViewById(R.id.transaction_amount_label);
        transactionAmount.setText(String.valueOf(expense.getAmount()));

        TextView transactionDate = findViewById(R.id.transaction_date_label);
        transactionDate.setText(expense.getDate());

        TextView transactionCategory = findViewById(R.id.transaction_category_label);
        transactionCategory.setText(expense.getCategoryName());
    }

    private void deleteTransaction() {
        // Prompt the ErrorDialog to the user
        ErrorDialog confirmationDelete = new ErrorDialog(this, "Confirmation", "Are you sure you want to delete this transaction?");
        confirmationDelete.showDeleteConfirmation(this::deleteTransactionFromDatabase);
    }

    private void deleteTransactionFromDatabase() {
        dbHandler.open();

        dbHandler.deleteTransaction(transactionId);

        dbHandler.close();

        ErrorDialog confirmation = new ErrorDialog(this, "Confirmation", "Transaction deleted successfully");
        // Go back to the expense history after the user acknowledges the dialog
        confirmation.showAcknowledgment(this::backToRestaurant);
    }

    public void backToRestaurant() {
        Intent intent = new Intent(this, DashboardViewListActivity.class);
        startActivity(intent);
    }
}