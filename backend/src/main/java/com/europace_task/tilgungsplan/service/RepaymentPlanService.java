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
    private final BigDecimal NINETY_NINE = BigDecimal.valueOf(99);

    public RepaymentPlan generateRepaymentPlan(RepaymentPlanInput repaymentPlanInput) {
        repaymentPlanInput = sanitizedRepaymentPlanInput(repaymentPlanInput);

        BigDecimal initialDebt = repaymentPlanInput.getLoanAmount().negate().setScale(2, RoundingMode.HALF_EVEN);
        RepaymentPlan repaymentPlan = new RepaymentPlan(initialDebt, repaymentPlanInput);

        if (repaymentPlanInput.getInterestRateFixation() == 0) {
            return repaymentPlan;
        }

        LocalDate startDate = YearMonth.now().atEndOfMonth();
        YearMonth endMonth = YearMonth.now().plusYears(repaymentPlanInput.getInterestRateFixation());

        long amountOfMonths = ChronoUnit.MONTHS.between(YearMonth.now(), endMonth);

        RepaymentPlanEntry initialRepayment = new RepaymentPlanEntry(startDate, initialDebt,
                BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN), initialDebt, initialDebt);
        repaymentPlan.getRepaymentPlanEntries().add(initialRepayment);

        BigDecimal repaymentRate = calculateRate(initialDebt, repaymentPlanInput.getShouldInterest(),
                repaymentPlanInput.getInitialRepaymentPercent());

        for (int i = 1; i <= amountOfMonths; i++) {
            if (repaymentPlan.getResidualDebt().compareTo(BigDecimal.ZERO) == 0) {
                return repaymentPlan;
            }
            createEntry(repaymentPlan, startDate.plusMonths(i), repaymentPlanInput.getShouldInterest(), repaymentRate);
        }

        return repaymentPlan;
    }

    private BigDecimal calculateRate(BigDecimal initialDebt, BigDecimal interestPercentage, BigDecimal repaymentPercentage) {
        BigDecimal combinedPercentage = interestPercentage.add(repaymentPercentage)
                .divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN);
        return initialDebt.abs().multiply(combinedPercentage).divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_EVEN);
    }

    private void createEntry(RepaymentPlan repaymentPlan, LocalDate entryDate, BigDecimal interestPercentage,
                             BigDecimal repaymentRate) {
        BigDecimal interest = calculateMonthlyInterest(repaymentPlan.getResidualDebt().abs(), interestPercentage);
        BigDecimal repayment = repaymentRate.subtract(interest);

        // if future residual debt is greater than 0 set repayment to difference between ZERO and residual debt
        if (repaymentPlan.getResidualDebt().add(repayment).compareTo(BigDecimal.ZERO) > 0) {
            repayment = BigDecimal.ZERO.subtract(repaymentPlan.getResidualDebt());
            repaymentRate = repayment.add(interest);
        }

        insertEntry(repaymentPlan, entryDate, interest, repayment, repaymentRate);
    }

    private void insertEntry(RepaymentPlan repaymentPlan, LocalDate entryDate, BigDecimal interest, BigDecimal repayment,
                             BigDecimal repaymentRate) {
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

    private RepaymentPlanInput sanitizedRepaymentPlanInput(RepaymentPlanInput repaymentPlanInput) {
        return new RepaymentPlanInput(
                repaymentPlanInput.getLoanAmount().abs(),
                safePercentage(repaymentPlanInput.getShouldInterest()),
                safePercentage(repaymentPlanInput.getInitialRepaymentPercent()),
                repaymentPlanInput.getInterestRateFixation());
    }

    private BigDecimal safePercentage(BigDecimal percentage) {
        return NINETY_NINE.min(percentage.abs());
    }

}
