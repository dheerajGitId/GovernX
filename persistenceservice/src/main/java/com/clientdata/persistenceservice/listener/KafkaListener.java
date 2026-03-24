package com.clientdata.persistenceservice.listener;

import com.clientdata.persistenceservice.exception.PersistenceServiceException;
import com.clientdata.persistenceservice.service.PlatinumPolicyIngestion;
import com.clientdata.schemas.model.GoldProcessedResponseBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaListener {
    private final PlatinumPolicyIngestion platinumPolicyIngestion;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "gold-to-platinum-topic",
            groupId = "persistenceservice-dev-group"
    )
    public void consumedGoldProcessedMessage(GoldProcessedResponseBody message) {
        log.info("Received message from Kafka: {}", message);

        if (message == null) {
            throw new PersistenceServiceException("Message is null");
        } else if (message.getPolicyDocumentGold() == null) {
            throw new PersistenceServiceException("Silver documents list is null or empty");
        }
        log.info("Received message from Kafka: {}", message);
        platinumPolicyIngestion.policyIngestion(message);
    }
}
