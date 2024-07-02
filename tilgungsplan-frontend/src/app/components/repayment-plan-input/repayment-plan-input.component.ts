import { Component, EventEmitter, Output } from '@angular/core';
import { RepaymentPlanInput } from '../../models/repayment-plan-input.model';
import { RepaymentPlanService } from 'src/app/services/repayment-plan.service';
import { RepaymentPlan } from 'src/app/models/repayment-plan.model';

@Component({
  selector: 'app-repayment-plan-input',
  templateUrl: './repayment-plan-input.component.html',
  styleUrls: ['./repayment-plan-input.component.scss']
})
export class RepaymentPlanInputComponent {
  repaymentPlanInput: RepaymentPlanInput = new RepaymentPlanInput();

  @Output()
  repaymentPlan: EventEmitter<RepaymentPlan> = new EventEmitter<RepaymentPlan>();

  constructor(private repaymentPlanService: RepaymentPlanService) {}

  generateRepaymentPlan() {
    this.repaymentPlan.emit(undefined);
    this.repaymentPlanService.generatePlan(this.repaymentPlanInput).subscribe({
      next: (repaymentPlan) => {
        repaymentPlan.repaymentPlanEntries = repaymentPlan.repaymentPlanEntries.map(entry => {
          entry.date = new Date(entry.date);
          return entry;
        })
        this.repaymentPlan.emit(repaymentPlan);
      }
    });
  }

  possiblySubmit(event: KeyboardEvent) {
    if (event.key === 'Enter' && this.inputValid()) {
      this.generateRepaymentPlan();
    }
  }

  inputValid(): boolean {
    return Boolean(this.repaymentPlanInput.loanAmount 
      && this.repaymentPlanInput.shouldInterest
      && this.repaymentPlanInput.initialRepaymentPercent
      && this.repaymentPlanInput.interestRateFixation);
  }

}
