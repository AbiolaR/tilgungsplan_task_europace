package com.europace_task.tilgungsplan.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class RepaymentPlan {
    private RepaymentPlanInput repaymentPlanInput;
    private BigDecimal residualDebt; // Restschuld
    private BigDecimal totalInterest = BigDecimal.ZERO; // Gesamt Zinsen
    private BigDecimal totalRepayment = BigDecimal.ZERO; // Gesamt Tilgung
    private BigDecimal totalRepaymentRate = BigDecimal.ZERO; // Gesamte Tilgungsrate

    @Setter(AccessLevel.NONE)
    private final List<RepaymentPlanEntry> repaymentPlanEntries = new ArrayList<>(); // Tilgungsplaneintrag

    public RepaymentPlan(BigDecimal initialDebt, RepaymentPlanInput repaymentPlanInput) {
        this.residualDebt = initialDebt;
        this.repaymentPlanInput = repaymentPlanInput;
    }
}
