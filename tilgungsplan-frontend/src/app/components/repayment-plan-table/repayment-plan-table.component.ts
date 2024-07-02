import { Component, Input } from '@angular/core';
import { RepaymentPlan } from 'src/app/models/repayment-plan.model';

@Component({
  selector: 'app-repayment-plan-table',
  templateUrl: './repayment-plan-table.component.html',
  styleUrls: ['./repayment-plan-table.component.scss']
})
export class RepaymentPlanTableComponent {
  @Input()
  repaymentPlan: RepaymentPlan = new RepaymentPlan();  

  columns = ['date', 'residualDebt', 'interest', 'repayment', 'repaymentRate'];
}
