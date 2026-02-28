package com.clientdata.policyservicetest.service;

import com.clientdata.policyservice.exception.PolicyServiceException;
import com.clientdata.policyservice.service.PolicyIngestService;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PolicyServiceIngestTest {
    @Mock
    PolicyDocumentBronzeRepo repo;

    PolicyIngestService service;

    @BeforeEach
    public void setUp() {
        repo = mock(PolicyDocumentBronzeRepo.class);
        service = new PolicyIngestService(repo);
    }

    @Test
    void PolicyDocumentIngestIdDoesNotExist(){
        PolicyDocumentBronze document = new PolicyDocumentBronze();

        when(repo.existsByPolicyId(anyString())).thenReturn(false);

        service.PolicyDocumentIngest(document);
        verify(repo, times(1)).save(document);

    }

    @Test
    void PolicyDocumentIngestIdExist(){
        PolicyDocumentBronze document = new PolicyDocumentBronze();

        when(repo.existsByPolicyId(anyString())).thenReturn(true);

        assertThrows(PolicyServiceException.class, () -> service.PolicyDocumentIngest(document));

    }


}
