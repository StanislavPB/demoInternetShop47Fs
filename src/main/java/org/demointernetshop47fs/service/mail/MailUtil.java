package org.demointernetshop47fs.service.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final Configuration freemakerConfiguration;
    private final JavaMailSender javaMailSender;

    private String createConfirmationMail(String firstName, String lastName, String link){

        try {
            Template template = freemakerConfiguration.getTemplate("confirm_registration_mail.ftlh");
            Map<Object, Object> model = new HashMap<>();
            model.put("firstNameLetter", firstName);
            model.put("lastNameLetter", lastName);
            model.put("linkLetter", link);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void send(String firstName, String lastName, String link, String subject, String email){

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");

        // String subject = "Code confirmation email";

        try {
            // прописать данные для письма
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(createConfirmationMail(firstName,lastName,link),true);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }

        javaMailSender.send(message);
    }

}
