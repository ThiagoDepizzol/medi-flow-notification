package com.medi.flow.notification.consumer.consultation;

import com.medi.flow.notification.dto.consultation.ConsultationEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class ConsultationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationEventListener.class);

    @KafkaListener(topics = "consultation-events", groupId = "notification-group")
    public void listen(ConsultationEventDTO event) {

        logger.info("listen() -> {}", event);

    }
}
