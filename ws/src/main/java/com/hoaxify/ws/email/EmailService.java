package com.hoaxify.ws.email;

import com.hoaxify.ws.config.HoaxifyProperties;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    JavaMailSenderImpl javaMailSender;

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    // bu servis oluşunca bu fonksiyonu da oluştur
    @PostConstruct
    public void initialize() {
        this.javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(hoaxifyProperties.getEmail().host());
        javaMailSender.setPort(hoaxifyProperties.getEmail().port());
        javaMailSender.setUsername(hoaxifyProperties.getEmail().username());
        javaMailSender.setPassword(hoaxifyProperties.getEmail().password());

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
    }

    String activationMail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;

    public void sendActivationMail(String mail, String activationToken) {
        var activationUrl = hoaxifyProperties.getClient().host() + "/activation/" + activationToken;
        var title= Messages.getMessageForLocale("hoaxify.mail.user.created.title", LocaleContextHolder.getLocale());
        var clickHere = Messages.getMessageForLocale("hoaxify.mail.click.here", LocaleContextHolder.getLocale());

        var mailBody = activationMail.replace("${url}", activationUrl).replace("${title}", title).replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        try {
            message.setFrom(hoaxifyProperties.getEmail().from());
            message.setTo(mail);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        this.javaMailSender.send(mimeMessage);
    }
}
