package com.nandan.user.security.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nandan.user.email.EmailSender;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailSender {
	
	private final static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	@Async
	public void send(String to, String email) {

		try {
			
			MimeMessage mimeMessage = javaMailSender
					.createMimeMessage();
			MimeMessageHelper helper = 
					new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("confirmed your email");
			helper.setFrom("nandandubey44@gmail.com");
			javaMailSender.send(mimeMessage);
		} catch (Exception e1) {
			LOGGER.error("Failed to email:::"+e1);
			throw new IllegalStateException("failed to send email");
		} 
		
	}

	
}
