package com.example.expensetracker.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDbContext extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_EXPENSES = "Expenses";
    public static final String TABLE_CATEGORIES = "Categories";

    // Column names for the Expenses table
    public static final String KEY_EXPENSE_TRANSACTION_ID = "TransactionId";
    public static final String KEY_EXPENSE_NAME = "ExpenseName";
    public static final String KEY_EXPENSE_AMOUNT = "ExpenseAmount";
    public static final String KEY_EXPENSE_DATE = "ExpenseDate";
    public static final String KEY_EXPENSE_CATEGORY_ID = "CategoryId";

    // Column names for the Categories table
    public static final String KEY_CATEGORY_ID = "CategoryId";
    public static final String KEY_CATEGORY_NAME = "CategoryName";
    public static final String KEY_CATEGORY_BUDGET = "CategoryBudget";


    // SQL statement to create the Expenses table
    private static final String SQL_CREATE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES + " (" +
            KEY_EXPENSE_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_EXPENSE_NAME + " TEXT NOT NULL, " +
            KEY_EXPENSE_AMOUNT + " INTEGER NOT NULL, " +
            KEY_EXPENSE_DATE + " TEXT NOT NULL, " +
            KEY_EXPENSE_CATEGORY_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + KEY_EXPENSE_CATEGORY_ID + ") REFERENCES Categories(" + KEY_CATEGORY_ID + "))";

    // SQL statement to create the Categories table
    private static final String SQL_CREATE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
            KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_CATEGORY_NAME + " TEXT NOT NULL, " +
            KEY_CATEGORY_BUDGET + " INTEGER NOT NULL)";

    private static final String SQL_DROP_EXPENSES = "DROP TABLE IF EXISTS " + TABLE_EXPENSES;
    private static final String SQL_DROP_CATEGORIES = "DROP TABLE IF EXISTS " + TABLE_CATEGORIES;

    public ExpenseDbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXPENSES);
        db.execSQL(SQL_CREATE_CATEGORIES);

        // Insert default categories
        setInitialCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_EXPENSES);
        db.execSQL(SQL_DROP_CATEGORIES);
        onCreate(db);
    }

    private void setInitialCategories(SQLiteDatabase db) {
        String[] initialCategories = {
                "Restaurants",
                "Groceries",
                "Travel",
                "Entertainment",
                "Health",
                "Other"
        };

        int[] initialBudgets = {
                100,
                400,
                2000,
                50,
                500,
                1000
        };

        for (int i = 0; i < initialCategories.length; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_CATEGORY_NAME, initialCategories[i]);
            values.put(KEY_CATEGORY_BUDGET, initialBudgets[i]);
            db.insert(TABLE_CATEGORIES, null, values);
        }
    }
}
