package com.clientdata.policyservice.controller;

import com.clientdata.policyservice.service.CustomerIngest;
import com.clientdata.policyservice.service.PolicyIngestService;
import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.model.ResponseBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("GovernX/PolicyService/")
@AllArgsConstructor
public class PolicyServiceController {
    private final CustomerIngest customerIngest;
    private final PolicyIngestService policyIngestService;

    @RequestMapping(value = "customerIngest", method = POST)
    public ResponseBody customerIngest(@RequestBody Customer customer) {
        return customerIngest.saveCustomerDetails(customer);
    }

    @RequestMapping(value = "policyIngest", method = POST)
    public void policyIngest(@RequestBody PolicyDocumentBronze document) {
        policyIngestService.PolicyDocumentIngest(document);
    }
}
