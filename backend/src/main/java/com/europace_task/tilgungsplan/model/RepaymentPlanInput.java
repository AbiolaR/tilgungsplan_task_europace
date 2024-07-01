package com.europace_task.tilgungsplan.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter(AccessLevel.NONE)
public class RepaymentPlanInput {
    private BigDecimal loanAmount; // Darlehensbetrag
    private BigDecimal shouldInterest; // Sollzins
    private BigDecimal initialRepaymentPercent; // anfängliche Tilgung
    private int interestRateFixation; // Zinsbindung

    public RepaymentPlanInput(BigDecimal loanAmount, BigDecimal shouldInterest, BigDecimal initialRepaymentPercent, int interestRateFixation) {
        this.loanAmount = loanAmount;
        this.shouldInterest = shouldInterest;
        this.initialRepaymentPercent = initialRepaymentPercent;
        this.interestRateFixation = interestRateFixation;
    }
}
