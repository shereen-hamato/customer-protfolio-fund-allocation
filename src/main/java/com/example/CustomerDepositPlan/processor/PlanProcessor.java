package com.example.CustomerDepositPlan.processor;

import com.example.CustomerDepositPlan.model.DepositPlan;
import com.example.CustomerDepositPlan.model.FundDeposit;
import com.example.CustomerDepositPlan.model.Portfolio;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlanProcessor {

    private List<DepositPlan> depositPlanList;
    private List<FundDeposit> fundDepositList;
    private Set<Portfolio> customerPortfolioList;

    public PlanProcessor(List<DepositPlan> depositPlanList, List<FundDeposit> fundDepositList) {
        this.depositPlanList = depositPlanList;
        this.fundDepositList = fundDepositList;
        this.customerPortfolioList = depositPlanList
                .stream()
                .map(DepositPlan::getPortfolioList)
                .flatMap(Collection::stream)
                .map(portfolio -> new Portfolio(portfolio.getName(), 0.0))
                .collect(Collectors.toSet());
    }


    public Set<Portfolio> distributeFund() {
        Optional<DepositPlan> oneTimePlan = depositPlanList
                .stream()
                .filter(this::isOneTimePlanType)
                .findFirst();

        if (depositPlanList.size() > 1 && oneTimePlan.isPresent()) {
            addFundToCustomerPortfoliosAccordingToDepositPlan(oneTimePlan.get());
            depositPlanList.remove(oneTimePlan.get());
        }

        while (!fundDepositList.isEmpty()) {
            for (DepositPlan depositPlan : depositPlanList) {
                addFundToCustomerPortfoliosAccordingToDepositPlan(depositPlan);
            }
        }

        return customerPortfolioList;
    }

    private boolean isOneTimePlanType(DepositPlan depositPlan) {
        return depositPlan.getType().equals("1");
    }

    private void addFundToCustomerPortfoliosAccordingToDepositPlan(DepositPlan depositPlan) {
        for (Portfolio planPortfolio : depositPlan.getPortfolioList()) {
            Portfolio matchingCustomerPortfolio = customerPortfolioList
                    .stream()
                    .filter(planPortfolio::equals)
                    .findFirst().get();

            addFundToPortfolio(planPortfolio, matchingCustomerPortfolio, fundDepositList);
        }
    }

//todo: documentations
    private void addFundToPortfolio(Portfolio planPortfolio, Portfolio customerPortfolio, List<FundDeposit> fundDepositList) {

        Double amountNeedToBeAdded = planPortfolio.getAmount();

        while (amountNeedToBeAdded > 0 && !fundDepositList.isEmpty()) {
            FundDeposit fund = fundDepositList.get(0);
            Double amountToBeAdded = fund.getAmount() > amountNeedToBeAdded ? amountNeedToBeAdded : fund.getAmount();

            customerPortfolio.setAmount(customerPortfolio.getAmount() + amountToBeAdded);
            fund.setAmount(fund.getAmount() - amountToBeAdded);

            if (fund.getAmount() == 0) {
                fundDepositList.remove(fund);
            }
            amountNeedToBeAdded = planPortfolio.getAmount() - amountToBeAdded;

        }

    }

}
