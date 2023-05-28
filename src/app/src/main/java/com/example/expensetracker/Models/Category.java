package com.example.expensetracker.Models;

public class Category {
    private int categoryId;
    private String name;
    private int budget;

    public Category() {
    }

    public Category(int categoryId, String name, int budget) {
        this.categoryId = categoryId;
        this.name = name;
        this.budget = budget;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
