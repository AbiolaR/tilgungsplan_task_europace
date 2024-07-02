import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RepaymentPlanTableComponent } from './components/repayment-plan-table/repayment-plan-table.component';
import { RepaymentPlanInputComponent } from './components/repayment-plan-input/repayment-plan-input.component';
import { RepaymentPlanContainerComponent } from './components/repayment-plan-container/repayment-plan-container.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule } from '@angular/common/http';
import {MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { TwoDecimalNumberDirective } from './directives/two-decimal-number.directive';
import { TwoDecimalPercentageDirective } from './directives/two-decimal-percentage.directive';
import { MinZeroNumberDirective } from './directives/min-zero-number.directive';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    RepaymentPlanTableComponent,
    RepaymentPlanInputComponent,
    RepaymentPlanContainerComponent,
    TwoDecimalNumberDirective,
    TwoDecimalPercentageDirective,
    MinZeroNumberDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    MatTableModule,
    MatInputModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
