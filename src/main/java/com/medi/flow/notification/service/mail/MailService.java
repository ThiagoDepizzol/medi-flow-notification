package com.medi.flow.notification.service.mail;

import com.medi.flow.notification.dto.consultation.ConsultationEventDTO;
import jakarta.mail.internet.MimeMessage;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender mailSender, final TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendConsultationMail(@NonNull final ConsultationEventDTO dto) {

        logger.info("sendConsultationMail() -> {}", dto);

        try {

            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            final Context context = new Context();

            context.setVariable("name", dto.getPatientName());
            context.setVariable("email", dto.getPatientEmail());
            context.setVariable("isNew", dto.getIsNew());
            context.setVariable("consultationDate", dto.getConsultationDate());
            context.setVariable("startTime", dto.getStartTime());
            context.setVariable("endTime", dto.getEndTime());

            final String html = templateEngine.process("email/consultation-email", context);

            helper.setTo(dto.getPatientEmail());
            helper.setSubject(dto.getIsNew() ? "Uma nova consulta foi agendada" : "Consulta atualizada");
            helper.setText(html, true);
            helper.setFrom("notification.fiap@mediflow.com.br");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }

    }
}
