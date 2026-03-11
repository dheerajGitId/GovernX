package com.clientdata.enrichmentservice.publisher;

import com.clientdata.schemas.enums.Message;
import com.clientdata.schemas.model.KafkaResponseBody;
import com.clientdata.schemas.model.SilverProcessedResponseBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaResponseBody publishSilverProcessingDocumentToGold(SilverProcessedResponseBody silverProcessedResponseBody) {

        KafkaResponseBody kafkaResponseBody = new KafkaResponseBody();
        String topic = "silver-to-gold-topic";

        try {

            kafkaTemplate.send(topic, silverProcessedResponseBody)
                    .whenComplete((result, ex) -> {

                        if (ex != null) {
                            log.error("Failed to send message to Kafka topic: {}", topic, ex);
                        } else {
                            log.info("Message sent successfully to topic: {}", topic);
                        }

                    });

            kafkaResponseBody.setMessage(Message.SUCCESS);

        } catch (Exception e) {
            log.error("Kafka send exception", e);
            kafkaResponseBody.setMessage(Message.FAILURE);
        }

        kafkaResponseBody.setId(UUID.randomUUID().toString());
        kafkaResponseBody.setTimestamp(new Date());

        return kafkaResponseBody;
    }
}
