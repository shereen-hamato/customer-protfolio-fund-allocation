package com.example.CustomerDepositPlan.processor;

import com.example.CustomerDepositPlan.model.DepositPlan;
import com.example.CustomerDepositPlan.model.FundDeposit;
import com.example.CustomerDepositPlan.model.Portfolio;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
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
        Optional<DepositPlan> oneTimePlan = depositPlanList.stream().filter(depositPlan -> depositPlan.getType().equals("1")).findFirst();

        if (depositPlanList.size() > 1 && oneTimePlan.isPresent()) {
            addFundToPortfolioAccordingToDepositPlan(oneTimePlan.get());
            depositPlanList.remove(oneTimePlan.get());
        }

        while (!fundDepositList.isEmpty()) {
            for (DepositPlan depositPlan : depositPlanList) {
                addFundToPortfolioAccordingToDepositPlan(depositPlan);
            }
        }

        return customerPortfolioList;
    }

    private void addFundToPortfolioAccordingToDepositPlan(DepositPlan depositPlan) {
        for (Portfolio planPortfolio : depositPlan.getPortfolioList()) {
            Portfolio matchingCustomerPortfolio = customerPortfolioList
                    .stream()
                    .filter(planPortfolio::equals)
                    .findFirst().get();

            distributeFundForPlanPortfolio(planPortfolio, matchingCustomerPortfolio, fundDepositList);
        }
    }

//todo: documentations
    private void distributeFundForPlanPortfolio(Portfolio planPortfolio, Portfolio customerPortfolio, List<FundDeposit> fundDepositList) {

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
