package com.bortolanza.notificacao.business;

import com.bortolanza.notificacao.business.dto.TasksDTO;
import com.bortolanza.notificacao.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${send.email.from}")
    public String from;

    @Value("${send.email.fromName}")
    private String fromName;

    public void sendEmail(TasksDTO dto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(from, fromName));
            helper.setTo(InternetAddress.parse(dto.getUserEmail()));
            helper.setSubject("Task Notification");

            Context context = new Context();
            context.setVariable("taskName", dto.getTaskName());
            context.setVariable("eventDate", dto.getEventDate());
            context.setVariable("description", dto.getTaskDescription());
            String template = templateEngine.process("notification", context);
            helper.setText(template, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e){
            throw new EmailException("Something went wrong while sending email", e.getCause());
        }
    }

}
