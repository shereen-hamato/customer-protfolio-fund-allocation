package com.example.CustomerDepositPlan.processor;

import com.example.CustomerDepositPlan.model.DepositPlan;
import com.example.CustomerDepositPlan.model.FundDeposit;
import com.example.CustomerDepositPlan.model.Portfolio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanProcessorTest {

    private List<DepositPlan> depositPlanList;

    @Before
    public void init() {
        depositPlanList = new ArrayList<>();
        DepositPlan plan1 = new DepositPlan();
        plan1.setPortfolioList(getPortfolioList(2, 10000.0, 100.0));
        plan1.setType("1");
        depositPlanList.add(plan1);
        DepositPlan plan2 = new DepositPlan();
        plan2.setPortfolioList(getPortfolioList(2, 0.0, 500.0));
        //plan2.setType("2");
        depositPlanList.add(plan2);
    }


    @Test
    public void givenFundDepositEquivalentToPlanAmount_allFundIsDistributed() {

        List<FundDeposit> fundDepositList = getFundDepositList(2, 10000.0, 600.0);

        PlanProcessor planProcessor = new PlanProcessor(depositPlanList, fundDepositList);
        Set<Portfolio> portfolioList = planProcessor.distributeFund();

        Double totalInPortfolio = portfolioList.stream().map(Portfolio::getAmount).reduce(Double::sum).get();

        assertEquals(new Double(10600.0), totalInPortfolio);


    }

    @Test
    public void givenFundDepositMoreThanPlanAmount_allFundIsDistributed() {
        List<FundDeposit> fundDepositList = getFundDepositList(2, 15000.0, 1600.0);

        PlanProcessor planProcessor = new PlanProcessor(depositPlanList, fundDepositList);
        Set<Portfolio> portfolioList = planProcessor.distributeFund();

        Double totalInPortfolio = portfolioList.stream().map(Portfolio::getAmount).reduce(Double::sum).get();

        assertEquals(new Double(16600.0), totalInPortfolio);


    }

    @Test
    public void givenFundDepositLessThanPlanAmount_allFundIsDistributed() {

        List<FundDeposit> fundDepositList = getFundDepositList(2, 5000.0, 100.0);

        PlanProcessor planProcessor = new PlanProcessor(depositPlanList, fundDepositList);
        Set<Portfolio> portfolioList = planProcessor.distributeFund();

        Double totalInPortfolio = portfolioList.stream().map(Portfolio::getAmount).reduce(Double::sum).get();

        assertEquals(new Double(5100.0), totalInPortfolio);


    }

    private List<Portfolio> getPortfolioList(Integer count, Double... amount) {

        List<Portfolio> portfolioList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            portfolioList.add(new Portfolio("Portfolio" + i, amount[i]));
        }
        return portfolioList;
    }

    private List<FundDeposit> getFundDepositList(Integer count, Double... amount) {

        List<FundDeposit> portfolioList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            portfolioList.add(new FundDeposit(amount[i]));
        }
        return portfolioList;
    }
}
