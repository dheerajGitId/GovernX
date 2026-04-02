package com.clientdata.policyservicetest.service;

import com.clientdata.policyservice.exception.PolicyServiceException;
import com.clientdata.policyservice.service.PolicyIngestService;
import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PolicyServiceIngestTest {
    @Mock
    PolicyDocumentBronzeRepo repo;

    @Mock
    PolicyAuditRepo repo2;


    PolicyIngestService service;

    @BeforeEach
    public void setUp() {
        repo = mock(PolicyDocumentBronzeRepo.class);
        repo2 = mock(PolicyAuditRepo.class);
        service = new PolicyIngestService(repo, repo2);
    }

    @Test
    void testPolicyDocumentIngestIdDoesNotExist() {
        PolicyDocumentBronze document = new PolicyDocumentBronze();
        String policyId = "PPP-C37B298";
        String customerId = "CCC-D808158";

        Customer customer1 = new Customer();
        customer1.setName("Gary");
        when(repo.existsByPolicyId(anyString())).thenReturn(false);
        when(repo2.existsByAuditId(anyString())).thenReturn(false);

        document.setCustomerId(customerId);

        customer1.setPolicyIds(singletonList(policyId));

        service.PolicyDocumentIngest(document);
        verify(repo, times(1)).save(document);
    }

    @Test
    void testPolicyDocumentIngestIdExist() {
        PolicyDocumentBronze document = new PolicyDocumentBronze();

        when(repo.existsByPolicyId(anyString())).thenReturn(true);

        assertThrows(PolicyServiceException.class, () -> service.PolicyDocumentIngest(document));

    }

    @Test
    void testAuditIdForPolicyDocumentExists() {
        when(repo2.existsByAuditId(anyString())).thenReturn(true);
        assertThrows(PolicyServiceException.class, () -> service.PolicyDocumentIngest(new PolicyDocumentBronze()));
    }

    @Test
    void testPolicyIdAndAuditIdForPolicyDocumentExists() {
        when(repo.existsByPolicyId(anyString())).thenReturn(true);
        when(repo2.existsByAuditId(anyString())).thenReturn(true);
        assertThrows(PolicyServiceException.class, () -> service.PolicyDocumentIngest(new PolicyDocumentBronze()));
    }


}
