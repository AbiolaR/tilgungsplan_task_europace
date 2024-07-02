export interface RepaymentPlanEntry {
    date: Date;
    residualDebt: number;
    interest: number;
    repayment: number;
    repaymentRate: number;
}