package com.europace_task.tilgungsplan.service;

import com.europace_task.tilgungsplan.model.RepaymentPlan;
import com.europace_task.tilgungsplan.model.RepaymentPlanInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class RepaymentPlanServiceTest {

    @Test
    void generateRepaymentPlan() {
        RepaymentPlanService repaymentPlanService = new RepaymentPlanService();

        // Defined input values
        final BigDecimal loanAmount = BigDecimal.valueOf(100000);
        final BigDecimal shouldInterest = BigDecimal.valueOf(2.12);
        final BigDecimal initialRepaymentPercent = BigDecimal.valueOf(2);
        final int interestRateFixation = 10;

        // Expected output values
        final int amountOfMonthsInTenYears = 121;
        final BigDecimal residualDebt = BigDecimal.valueOf(-77744.14);
        final BigDecimal totalInterest = BigDecimal.valueOf(18943.74);
        final BigDecimal totalRepayment = BigDecimal.valueOf(22255.86);
        final BigDecimal totalRepaymentRate = BigDecimal.valueOf(41199.60).setScale(2, RoundingMode.HALF_EVEN);

        RepaymentPlanInput repaymentPlanInput = new RepaymentPlanInput(loanAmount, shouldInterest, initialRepaymentPercent, interestRateFixation);

        RepaymentPlan repaymentPlan = repaymentPlanService.generateRepaymentPlan(repaymentPlanInput);

        assertEquals(amountOfMonthsInTenYears, repaymentPlan.getRepaymentPlanEntries().size());
        assertEquals(residualDebt, repaymentPlan.getResidualDebt());
        assertEquals(totalInterest, repaymentPlan.getTotalInterest());
        assertEquals(totalRepayment, repaymentPlan.getTotalRepayment());
        assertEquals(totalRepaymentRate, repaymentPlan.getTotalRepaymentRate());
    }
}