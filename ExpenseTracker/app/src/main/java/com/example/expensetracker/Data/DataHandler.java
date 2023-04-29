package com.example.expensetracker.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.Models.Category;
import com.example.expensetracker.Models.Expense;

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
            Cursor cursor = dbContext.rawQuery("SELECT * FROM " + TABLE_EXPENSES + " ORDER BY " + KEY_EXPENSE_DATE + " DESC", null);
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
            values.put(KEY_EXPENSE_AMOUNT, expense.getAmount());
            values.put(KEY_EXPENSE_DATE, expense.getDate());
            values.put(KEY_EXPENSE_CATEGORY_ID, expense.getCategoryId());

            dbContext.insert(TABLE_EXPENSES, null, values);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
