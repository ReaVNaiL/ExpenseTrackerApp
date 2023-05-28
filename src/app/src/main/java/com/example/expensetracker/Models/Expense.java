package com.example.expensetracker.Models;

public class Expense {
    private int transactionId;
    private String name;
    private float amount;
    private String date;
    private int categoryId;

    private String categoryName;

    public Expense(int expenseId, String name, float amount, String date, int categoryId, String categoryName) {
        this.transactionId = expenseId;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Expense() {
    }

    public void setTransactionId(int anInt) {
        this.transactionId = anInt;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
