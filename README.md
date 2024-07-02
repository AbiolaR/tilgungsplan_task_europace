
# Tilgungsplan Generator

Erstellt einen Tilgungsplan eines Darlehens mit gleichbleibender Rate mit folgenden input parametern.
- Darlehensbetrag
- Sollzins (in Prozent)
- anfängliche Tilgung (in Prozent)
- Zinsbindung (in Jahren)



## Run Locally

Clone the project

```bash
  git clone https://github.com/AbiolaR/tilgungsplan_task_europace.git
```

### Backend

Go to the project directory

```bash
  cd tilgungsplan_task_europace/backend
```

Start the backend server

```bash
  gradlew bootRun
```

### Frontend

Go to the project directory

```bash
  cd tilgungsplan_task_europace/tilgungsplan-frontend
```

Start the backend server

```bash
  gradlew bootRun
```

Open http://localhost:4200/ in your browser of choice
## Running Backend Tests

Go to the project directory

```bash
  cd tilgungsplan_task_europace/backend
```

To run tests, run the following command

```bash
  gradlew :test --tests "com.europace_task.tilgungsplan.service.RepaymentPlanServiceTest"
```


## API Reference

### Generate repayment plan

```http
  POST /api/v1/repaymentplan/generate
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `repaymentPlanInput` | `RepaymentPlanInput` | **Required**. ReplaymentPlanInput object containing the loan amount, should interest, initial repayment percentage and interest rate fixation duration |

#### `RepaymentPlanInput`
```json
{
    "loanAmount": 100000,
    "shouldInterest": 2.12,
    "initialRepaymentPercent": 2,
    "interestRateFixation": 10
}
```

#### returns

 | Type     |
 | :------- | 
 | `RepaymentPlan` | 

#### `RepaymentPlan`
```json
{
    "repaymentPlanInput": {
        "loanAmount": 100000,
        "shouldInterest": 2.12,
        "initialRepaymentPercent": 2,
        "interestRateFixation": 10
    },
    "residualDebt": -77744.14,
    "totalInterest": 18943.74,
    "totalRepayment": 22255.86,
    "totalRepaymentRate": 41199.60,
    "repaymentPlanEntries": [
        {
            "date": "2024-07-31",
            "residualDebt": -100000.00,
            "interest": 0.00,
            "repayment": -100000.00,
            "repaymentRate": -100000.00
        },
        {
            "date": "2024-08-31",
            "residualDebt": -99833.34,
            "interest": 176.67,
            "repayment": 166.66,
            "repaymentRate": 343.33
        },
        ...
        {
            "date": "2034-06-30",
            "residualDebt": -77949.76,
            "interest": 138.07,
            "repayment": 205.26,
            "repaymentRate": 343.33
        },
        {
            "date": "2034-07-31",
            "residualDebt": -77744.14,
            "interest": 137.71,
            "repayment": 205.62,
            "repaymentRate": 343.33
        }
    ]
}
```