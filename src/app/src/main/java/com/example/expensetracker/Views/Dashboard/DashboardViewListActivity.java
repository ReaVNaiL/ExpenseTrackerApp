package com.example.expensetracker.Views.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.expensetracker.Data.DataHandler;
import com.example.expensetracker.Models.Category;
import com.example.expensetracker.Models.Expense;
import com.example.expensetracker.R;
import com.example.expensetracker.Views.AddExpense.AddExpenseDialog;
import com.example.expensetracker.Views.TransactionDetails.TransactionDetailsActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewListActivity extends AppCompatActivity {
    private List<Expense> expenseList;
    private RecyclerView recyclerView;
    private BarChart barChart;
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

        barChart = findViewById(R.id.bar_chart);
        setupBarChart();

        Button backMenuButton = findViewById(R.id.back_menu_button);
        backMenuButton.setOnClickListener(v -> {
            finish();
        });
    }

    private Boolean setExpensesList() {
        try {
            dbHandler.open();
            expenseList = dbHandler.getAllExpenses();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addExpenses(View view) {
        AddExpenseDialog addExpenseDialog = new AddExpenseDialog(dbHandler);
        addExpenseDialog.show(getSupportFragmentManager(), "Add Expense Dialog");
    }

    private void setupBarChart() {
        List<Category> categories = dbHandler.getAllCategories();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> categoryNames = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            entries.add(new BarEntry(i, categories.get(i).getBudget()));
            categoryNames.add(categories.get(i).getName());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Categories");
        int barColor = Color.parseColor("#1A1E45");
        dataSet.setColor(barColor);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customize the appearance of the chart
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        // Disable right Y-axis
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);

        // Set category names at the bottom
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(categoryNames));
        xAxis.setGranularity(1f);

        // Set the text size and color for the X-axis labels
        int textColor = Color.parseColor("#1A1E45");
        xAxis.setTextColor(textColor);
        xAxis.setTextSize(12f);

        // Display values inside the bars
        dataSet.setValueTextSize(12f); // Set the text size as desired
        dataSet.setValueTextColor(Color.parseColor("#1A1E45"));

        barChart.invalidate();
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

    public void updateExpense(Expense expense) {
        Intent intent = new Intent(this, TransactionDetailsActivity.class);
        intent.putExtra("expenseId", expense.getTransactionId());
        startActivity(intent);
    }
}