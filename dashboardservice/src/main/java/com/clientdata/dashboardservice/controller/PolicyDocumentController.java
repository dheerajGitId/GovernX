package com.clientdata.dashboardservice.controller;

import com.clientdata.dashboardservice.service.PolicyDocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class PolicyDocumentController {
    private final PolicyDocumentService policyDocumentService;


}
