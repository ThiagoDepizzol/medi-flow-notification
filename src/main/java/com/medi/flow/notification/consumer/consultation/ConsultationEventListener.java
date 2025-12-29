package com.medi.flow.notification.consumer.consultation;

import com.medi.flow.notification.dto.consultation.ConsultationEventDTO;
import com.medi.flow.notification.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsultationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsultationEventListener.class);

    private final MailService mailService;

    public ConsultationEventListener(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = "consultation-events", groupId = "notification-group")
    public void listen(ConsultationEventDTO event) {

        logger.info("listen() -> {}", event);

        mailService.sendConsultationMail(event.getDoctorName(), event.getPatientName());


    }
}
