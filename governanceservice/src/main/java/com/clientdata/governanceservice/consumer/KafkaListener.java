package com.clientdata.governanceservice.consumer;

import com.clientdata.governanceservice.exception.GovernanceServiceException;
import com.clientdata.governanceservice.service.GoldProcessingService;
import com.clientdata.schemas.model.SilverProcessedResponseBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class KafkaListener {
    private final GoldProcessingService goldProcessingService;

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "silver-to-gold-topic",
            groupId = "governanceservice-dev-group"
    )
    public void consumeSilverProcessedMessage(SilverProcessedResponseBody message) {

        log.info("Received message from Kafka: {}", message);

        if (message == null) {
            throw new GovernanceServiceException("Message is null");
        }
        if (message.getSilverDocuments() == null || message.getSilverDocuments().isEmpty()) {
            throw new GovernanceServiceException("Silver documents list is null or empty");
        }
        goldProcessingService.ingestSilverDocuments(message.getSilverDocuments());

    }
}
