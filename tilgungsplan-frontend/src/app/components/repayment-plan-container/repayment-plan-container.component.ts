import { Component } from '@angular/core';
import { RepaymentPlan } from '../../models/repayment-plan.model';

@Component({
  selector: 'app-repayment-plan-container',
  templateUrl: './repayment-plan-container.component.html',
  styleUrls: ['./repayment-plan-container.component.scss']
})
export class RepaymentPlanContainerComponent {
  repaymentPlan: RepaymentPlan = new RepaymentPlan();

  repaymentPlanChanged(repaymentPlan: RepaymentPlan) {
    this.repaymentPlan = repaymentPlan;
  }
}
