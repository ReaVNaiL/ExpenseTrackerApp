package com.example.expensetracker.Views.AddExpense;

import static com.example.expensetracker.Helpers.DateUtility.createDateString;
import static com.example.expensetracker.Helpers.DateUtility.getCurrentDate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetracker.Data.DataHandler;
import com.example.expensetracker.Models.Category;
import com.example.expensetracker.Models.Expense;
import com.example.expensetracker.R;
import com.example.expensetracker.Views.Dashboard.DashboardViewListActivity;

import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddExpenseDialog extends DialogFragment {
    private DatePickerDialog datePickerDialog;
    private Button datePickerButton;
    private DataHandler dbContext;
    private EditText expenseNameEdit;
    private EditText expenseAmountEdit;
    private Spinner categorySpinner;

    public AddExpenseDialog(DataHandler context) {
        // Required empty public constructor
        this.dbContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NotNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_expense_dialog, container, false);

        // Set up dialog styling
        UpdateDialogStyling();

        // Set Up Classes
        setDatePickerDialog();
        setCategorySpinner(view);

        expenseNameEdit = view.findViewById(R.id.expense_name_text_field);
        expenseAmountEdit = view.findViewById(R.id.expense_amount_text_field);

        // Button set up
        datePickerButton = view.findViewById(R.id.date_picker_button);
        datePickerButton.setText(getCurrentDate());
        datePickerButton.setOnClickListener(this::openDatePicker);

        Button saveButton = view.findViewById(R.id.expense_save_button);
        saveButton.setOnClickListener(this::saveExpense);

        Button cancelButton = view.findViewById(R.id.expense_cancel_button);
        cancelButton.setOnClickListener(this::cancelExpense);

        return view;
    }

    private void UpdateDialogStyling() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getDialog().getWindow().getAttributes().windowAnimations = R.style.RoundStyleDialogTheme;
            getDialog().getWindow().setGravity(Gravity.CENTER);
        }
    }

    private void cancelExpense(View view) {
        dismiss();
    }

    private void saveExpense(View view) {
        Expense expense = new Expense();

        expense.setDate(datePickerButton.getText().toString());
        expense.setName(expenseNameEdit.getText().toString());
        expense.setAmount(Float.parseFloat(expenseAmountEdit.getText().toString()));
        expense.setCategoryName(categorySpinner.getSelectedItem().toString());

        // Get Category Id
        Category expenseCategory = dbContext.getCategory(expense.getCategoryName());
        expense.setCategoryId(expenseCategory.getCategoryId());

        Boolean isExpenseSaved = dbContext.insertExpense(expense);

        if (!isExpenseSaved) {
            return;
        }

        DashboardViewListActivity dashboardActivity = (DashboardViewListActivity) getActivity();

        if (dashboardActivity != null) {
            dashboardActivity.refreshExpensesList();
        }

        dismiss();
    }


    public void setDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = createDateString(year, month, day);
                datePickerButton.setText(date);
            }
        };

        Calendar arg = Calendar.getInstance();

        int year = arg.get(Calendar.YEAR);
        int month = arg.get(Calendar.MONTH);
        int day = arg.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    // Set Category Spinner
    private void setCategorySpinner(View view) {
        categorySpinner = view.findViewById(R.id.expense_category_spinner);

        List<Category> categories = dbContext.getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        // Create a custom adapter that sets the text color for each item
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                if (view != null) {
                    ((TextView) view).setTextColor(Color.WHITE);
                    ((TextView) view).setTextSize(20);
                }

                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(0, true);
    }

}
