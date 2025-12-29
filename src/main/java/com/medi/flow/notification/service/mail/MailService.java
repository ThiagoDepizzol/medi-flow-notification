package com.medi.flow.notification.service.mail;

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

    public void sendConsultationMail(@NonNull final String email, @NonNull final String name) {

        logger.info("sendConsultationMail() -> {}, {}", email, name);

        try {
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            final Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("email", email);

            final String html = templateEngine.process("email/consultation-email", context);

            helper.setTo(email);
            helper.setSubject("Bem-vindo ao sistema");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }

    }
}
