package com.europace_task.tilgungsplan.model;

import lombok.AccessLevel;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @param date          Datum
 * @param residualDebt  Restschuld
 * @param interest      Zinsen
 * @param repayment     Tilgung
 * @param repaymentRate Tilgungsrate
 */
@Setter(AccessLevel.NONE)
public record RepaymentPlanEntry(LocalDate date,
                                 BigDecimal residualDebt,
                                 BigDecimal interest,
                                 BigDecimal repayment,
                                 BigDecimal repaymentRate) {
}
