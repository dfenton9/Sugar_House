package com.sugarhouse.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

    	final String username = "FA16.605782@gmail.com";
		final String password = "classproject";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
	        if (bodyIsHTML) {
	            message.setContent(body, "text/html");
	        } else {
	            message.setText(body);
	        }
	        Transport transport = session.getTransport();
	        transport.connect(username, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
}    	
