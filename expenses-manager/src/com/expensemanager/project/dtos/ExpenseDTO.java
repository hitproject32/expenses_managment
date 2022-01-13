package com.expensemanager.project.dtos;

public class ExpenseDTO {
    private int categoryId;
    private String cost;
    private String currency;
    private String info;

    public ExpenseDTO(int categoryId, String cost, String currency, String info) {
        setCategoryId(categoryId);
        setCost(cost);
        setCurrency(currency);
        setInfo(info);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currencyName) {
        this.currency = currencyName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
