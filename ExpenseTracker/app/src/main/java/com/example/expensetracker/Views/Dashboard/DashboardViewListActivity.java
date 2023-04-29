package com.example.expensetracker.Views.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.expensetracker.Data.DataHandler;
import com.example.expensetracker.Models.Expense;
import com.example.expensetracker.R;
import com.example.expensetracker.Views.AddExpense.AddExpenseDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DashboardViewListActivity extends AppCompatActivity {
    private List<Expense> expenseList;

    private RecyclerView recyclerView;

    private DataHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_expenses);

        FloatingActionButton addExpenseButton = findViewById(R.id.add_expense_button);
        addExpenseButton.setOnClickListener(this::addExpenses);

        dbHandler = new DataHandler(this);
        recyclerView = findViewById(R.id.expenses_recycler_view);

        Boolean success = setExpensesList();

        if (!success) {
            return;
        }

        setLayoutManager();
        setItemAnimator();
        setExpensesAdapter();
    }

    private Boolean setExpensesList() {
        try {
            dbHandler.open();

            expenseList = dbHandler.getAllExpenses();

//            dbHandler.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addExpenses(View view) {
//        dbHandler.open();

        AddExpenseDialog addExpenseDialog = new AddExpenseDialog(dbHandler);
        addExpenseDialog.show(getSupportFragmentManager(), "Add Expense Dialog");

//        dbHandler.close();
    }

    public void refreshExpensesList() {
        setExpensesList();
        setExpensesAdapter();
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemAnimator() {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void setExpensesAdapter() {
        DashboardAdapter adapter = new DashboardAdapter(expenseList);
        recyclerView.setAdapter(adapter);
    }
}