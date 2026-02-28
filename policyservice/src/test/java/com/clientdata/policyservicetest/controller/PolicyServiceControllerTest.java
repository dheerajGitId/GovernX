package com.clientdata.policyservicetest.controller;

import com.clientdata.policyservice.controller.PolicyServiceController;
import com.clientdata.policyservice.service.CustomerIngest;
import com.clientdata.policyservice.service.PolicyIngestService;
import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class PolicyServiceControllerTest {
    @Mock
    PolicyIngestService policyService;

    @Mock
    CustomerIngest customerService;

    @Mock
    PolicyServiceController policyServiceController;

    MockMvc mockMvc;

    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        customerService = mock(CustomerIngest.class);
        policyService = mock(PolicyIngestService.class);
        policyServiceController = new PolicyServiceController(customerService, policyService);
        mockMvc = MockMvcBuilders.
                standaloneSetup(policyServiceController).
                build();
    }

    @Test
    void testCustomerIngest() throws Exception {

        Customer customer = new Customer();
        when(customerService.saveCustomerDetails(any(Customer.class)))
                .thenReturn(any());

        mockMvc.perform(post("/GovernX/PolicyService/customerIngest")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    void testPolicyIngest() throws Exception {
        mockMvc.perform(post("/GovernX/PolicyService/policyIngest")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new PolicyDocumentBronze())))
                .andExpect(status().isOk());
    }
}
