package com.example.CustomerDepositPlan.model;


import java.util.Objects;

public class Portfolio {
    private String name;
    private Double amount;

    public Portfolio() {
    }

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return name.equals(portfolio.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
