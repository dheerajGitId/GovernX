package com.clientdata.governanceservice.controller;

import com.clientdata.governanceservice.service.PolicyApprovalService;
import com.clientdata.schemas.model.ApproverView;
import com.clientdata.schemas.model.PolicyApprovalRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("GovernX/GovernanceService/")
@AllArgsConstructor
public class GovernanceServiceController {
    private final PolicyApprovalService policyApprovalService;

    @PostMapping("PolicyApproval")
    public List<ApproverView> policyApproval(@RequestBody PolicyApprovalRequestBody body) {
        return policyApprovalService.policyApproval(body.getPolicyId(), body.getApproverView());

    }
}
