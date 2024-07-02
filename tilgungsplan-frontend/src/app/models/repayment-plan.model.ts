import { RepaymentPlanEntry } from "./repayment-plan-entry.model";

export class RepaymentPlan {
    residualDebt: number = 0; // Restschuld
    totalInterest: number = 0; // Gesamt Zinsen
    totalRepayment: number = 0; // Gesamt Tilgung
    totalRepaymentRate: number = 0; // Gesamte Tilgungsrate

    repaymentPlanEntries: RepaymentPlanEntry[] = [];
}