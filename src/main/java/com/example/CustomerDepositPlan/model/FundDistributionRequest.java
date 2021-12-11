package com.example.CustomerDepositPlan.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class FundDistributionRequest {
    @NotEmpty
    private List<FundDeposit> fundDepositList;

    @Valid
    @NotEmpty
    private List<DepositPlan> depositPlanList;

    public List<FundDeposit> getFundDepositList() {
        return fundDepositList;
    }

    public void setFundDepositList(List<FundDeposit> fundDepositList) {
        this.fundDepositList = fundDepositList;
    }

    public List<DepositPlan> getDepositPlanList() {
        return depositPlanList;
    }

    public void setDepositPlanList(List<DepositPlan> depositPlanList) {
        this.depositPlanList = depositPlanList;
    }
}
