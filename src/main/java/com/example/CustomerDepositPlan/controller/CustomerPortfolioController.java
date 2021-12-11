package com.example.CustomerDepositPlan.controller;

import com.example.CustomerDepositPlan.model.FundDistributionRequest;
import com.example.CustomerDepositPlan.model.Portfolio;
import com.example.CustomerDepositPlan.processor.PlanProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(path = "/portfolio")
public class CustomerPortfolioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPortfolioController.class);



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Set<Portfolio> getPortfolios(@RequestBody @Validated FundDistributionRequest fundDistributionRequest) {
        PlanProcessor planProcessor = new PlanProcessor(fundDistributionRequest.getDepositPlanList(), fundDistributionRequest.getFundDepositList());
        return planProcessor.distributeFund();
    }

}
