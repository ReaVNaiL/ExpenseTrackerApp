package com.example.expensetracker.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.Models.Category;
import com.example.expensetracker.Models.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DataHandler extends ExpenseDbContext {
    private final ExpenseDbContext dbConnection;

    private SQLiteDatabase dbContext;

    public DataHandler(Context context) {
        super(context);
        dbConnection = new ExpenseDbContext(context);
    }

    public void open() throws SQLException  {
        dbContext = dbConnection.getWritableDatabase();
    }

    public void close() {
        dbConnection.close();
    }

    public Boolean addExpense(Expense expense) {
        try {
            ContentValues values = new ContentValues();

            values.put(KEY_EXPENSE_NAME, expense.getName());
            values.put(KEY_EXPENSE_AMOUNT, expense.getAmount());
            values.put(KEY_EXPENSE_DATE, expense.getDate());
            values.put(KEY_CATEGORY_NAME, expense.getCategoryId());

            dbContext.insert(TABLE_EXPENSES, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<>();

        try {
            Cursor cursor = dbContext.rawQuery("SELECT * FROM " + TABLE_EXPENSES + " ORDER BY substr(" + KEY_EXPENSE_DATE + ", -4) || " +
                    "substr('0'||(CASE substr(" + KEY_EXPENSE_DATE + ", 1, 3) WHEN 'JAN' THEN '01' WHEN 'FEB' THEN '02' WHEN 'MAR' THEN '03' " +
                    "WHEN 'APR' THEN '04' WHEN 'MAY' THEN '05' WHEN 'JUN' THEN '06' WHEN 'JUL' THEN '07' WHEN 'AUG' THEN '08' " +
                    "WHEN 'SEP' THEN '09' WHEN 'OCT' THEN '10' WHEN 'NOV' THEN '11' WHEN 'DEC' THEN '12' END), -2) || " +
                    "substr('0'||substr(" + KEY_EXPENSE_DATE + ", 5, 2), -2) DESC", null);
            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();

                    expense.setTransactionId(cursor.getInt(0));
                    expense.setName(cursor.getString(1));
                    expense.setAmount((float) cursor.getDouble(2));
                    expense.setDate(cursor.getString(3));
                    expense.setCategoryId(cursor.getInt(4));

                    expenseList.add(expense);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseList;
    }

    public Boolean insertExpense(Expense expense) {
        try {
            ContentValues values = new ContentValues();

            values.put(KEY_EXPENSE_NAME, expense.getName());

            // Format the amount to have a maximum of 2 decimal places
            BigDecimal amount = BigDecimal.valueOf(expense.getAmount()).setScale(2, RoundingMode.HALF_UP);

            // Ensure the amount has 2 decimal places, adding zeroes if necessary
            DecimalFormat df = new DecimalFormat("0.00");
            values.put(KEY_EXPENSE_AMOUNT, df.format(amount));

            values.put(KEY_EXPENSE_DATE, expense.getDate());
            values.put(KEY_EXPENSE_CATEGORY_ID, expense.getCategoryId());

            dbContext.insert(TABLE_EXPENSES, null, values);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Expense getTransactionDetails(int transactionId){
        Expense expense = new Expense();

        try {
            Cursor cursor = dbContext.rawQuery("SELECT * FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_TRANSACTION_ID + " = " + transactionId, null);

            if (cursor.moveToFirst()) {
                do {
//                    ["TransactionId", "ExpenseName", "ExpenseAmount", "ExpenseDate", "CategoryId"]
                    expense.setTransactionId(cursor.getInt(0));
                    expense.setName(cursor.getString(1));
                    expense.setAmount((float) cursor.getDouble(2));
                    expense.setDate(cursor.getString(3));
                    expense.setCategoryId(cursor.getInt(4));
                    expense.setCategoryName(getCategoryName(expense.getCategoryId()));
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expense;
    }

    public void deleteTransaction(int transactionId) {
        try {
            dbContext.delete(TABLE_EXPENSES, KEY_EXPENSE_TRANSACTION_ID + " = " + transactionId, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategory(String category) {
        Category newCategory = new Category();

        try {
            Cursor cursor = dbContext.rawQuery("SELECT *" +
                    " FROM " + TABLE_CATEGORIES +
                    " WHERE " + KEY_CATEGORY_NAME + " = " + "'" + category + "'",
                    null);

            if (cursor.moveToFirst()) {
                do {
                    newCategory.setCategoryId(cursor.getInt(0));
                    newCategory.setName(cursor.getString(1));
                    newCategory.setBudget((int) cursor.getDouble(2));
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newCategory;
    }

    public String getCategoryName(int categoryId) {
        String categoryName = "";

        try {
            Cursor cursor = dbContext.rawQuery("SELECT " + KEY_CATEGORY_NAME +
                    " FROM " + TABLE_CATEGORIES +
                    " WHERE " + KEY_CATEGORY_ID + " = " + categoryId,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    categoryName = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryName;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();

        try {
            Cursor cursor = dbContext.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);

            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();

                    category.setCategoryId(cursor.getInt(0));
                    category.setName(cursor.getString(1));
                    category.setBudget((int) cursor.getDouble(2));

                    categoryList.add(category);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }
}
