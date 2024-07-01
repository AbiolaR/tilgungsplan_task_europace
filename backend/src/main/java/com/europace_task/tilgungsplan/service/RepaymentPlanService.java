package com.europace_task.tilgungsplan.service;

import com.europace_task.tilgungsplan.model.RepaymentPlan;
import com.europace_task.tilgungsplan.model.RepaymentPlanEntry;
import com.europace_task.tilgungsplan.model.RepaymentPlanInput;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Service
public class RepaymentPlanService {
    private final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public RepaymentPlan generateRepaymentPlan(RepaymentPlanInput repaymentPlanInput) {
        BigDecimal initialDebt = repaymentPlanInput.getLoanAmount().negate();
        RepaymentPlan repaymentPlan = new RepaymentPlan(initialDebt);

        LocalDate startDate = YearMonth.now().atEndOfMonth();
        YearMonth endMonth = YearMonth.now().plusYears(repaymentPlanInput.getInterestRateFixation());

        long amountOfMonths = ChronoUnit.MONTHS.between(YearMonth.now(), endMonth);

        RepaymentPlanEntry initialRepayment = new RepaymentPlanEntry(startDate, initialDebt, BigDecimal.ZERO,
                initialDebt, initialDebt);
        repaymentPlan.getRepaymentPlanEntries().add(initialRepayment);

        BigDecimal repaymentRate = calculateRate(initialDebt, repaymentPlanInput.getShouldInterest(),
                repaymentPlanInput.getInitialRepaymentPercent());

        for (int i = 1; i <= amountOfMonths; i++) {
            createEntry(repaymentPlan, startDate.plusMonths(i), repaymentPlanInput.getShouldInterest(), repaymentRate);
        }

        return repaymentPlan;
    }

    private BigDecimal calculateRate(BigDecimal initialDebt, BigDecimal interestPercentage, BigDecimal repaymentPercentage) {
        BigDecimal combinedPercentage = interestPercentage.add(repaymentPercentage)
                .divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN);
        return initialDebt.abs().multiply(combinedPercentage).divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_EVEN);
    }

    private void createEntry(RepaymentPlan repaymentPlan, LocalDate entryDate, BigDecimal interestPercentage, BigDecimal repaymentRate) {
        BigDecimal interest = calculateMonthlyInterest(repaymentPlan.getResidualDebt().abs(), interestPercentage);
        BigDecimal repayment = repaymentRate.subtract(interest);

        insertEntry(repaymentPlan, entryDate, interest, repayment, repaymentRate);
    }

    private void insertEntry(RepaymentPlan repaymentPlan, LocalDate entryDate, BigDecimal interest, BigDecimal repayment, BigDecimal repaymentRate) {
        repaymentPlan.setTotalInterest(repaymentPlan.getTotalInterest().add(interest));
        repaymentPlan.setTotalRepayment(repaymentPlan.getTotalRepayment().add(repayment));
        repaymentPlan.setTotalRepaymentRate(repaymentPlan.getTotalRepaymentRate().add(repaymentRate));
        repaymentPlan.setResidualDebt(repaymentPlan.getResidualDebt().add(repayment));

        repaymentPlan.getRepaymentPlanEntries().add(new RepaymentPlanEntry(
                entryDate, repaymentPlan.getResidualDebt(), interest, repayment, repaymentRate));
    }

    private BigDecimal calculateMonthlyInterest(BigDecimal value, BigDecimal interestPercentage) {
        BigDecimal percentage = interestPercentage.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN);
        return value
                .multiply(percentage)
                .divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_EVEN);
    }

}
