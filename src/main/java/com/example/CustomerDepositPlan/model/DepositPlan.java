package com.example.CustomerDepositPlan.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class DepositPlan {
    private String type = "2";
    private String name;
    @NotEmpty
    private List<Portfolio> portfolioList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(List<Portfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
