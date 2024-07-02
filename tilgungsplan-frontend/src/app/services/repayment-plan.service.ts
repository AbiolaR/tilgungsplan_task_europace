import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RepaymentPlanInput } from '../models/repayment-plan-input.model';
import { Observable } from 'rxjs';
import { RepaymentPlan } from '../models/repayment-plan.model';

@Injectable({
  providedIn: 'root'
})
export class RepaymentPlanService {
  private apiUrl: string = 'http://localhost:8080/api/v1/repaymentplan/generate';
  constructor(private httpClient: HttpClient) { }

  generatePlan(repaymentPlanInput: RepaymentPlanInput): Observable<RepaymentPlan> {
    return this.httpClient.post<RepaymentPlan>(this.apiUrl, repaymentPlanInput);
  }
}
