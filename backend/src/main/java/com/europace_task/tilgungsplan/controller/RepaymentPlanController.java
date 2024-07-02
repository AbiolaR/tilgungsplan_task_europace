package com.europace_task.tilgungsplan.controller;

import com.europace_task.tilgungsplan.model.RepaymentPlan;
import com.europace_task.tilgungsplan.model.RepaymentPlanInput;
import com.europace_task.tilgungsplan.service.RepaymentPlanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/repaymentplan")
public class RepaymentPlanController {
    private final RepaymentPlanService repaymentPlanService;

    public RepaymentPlanController(RepaymentPlanService repaymentPlanService) {
        this.repaymentPlanService = repaymentPlanService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = {"generate", "generate/"})
    RepaymentPlan generateRepaymentPlan(@RequestBody RepaymentPlanInput repaymentPlanInput) {
        return repaymentPlanService.generateRepaymentPlan(repaymentPlanInput);
    }
}
