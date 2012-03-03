package com.engineplay.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	public void sendMail(String fromEmail, String fromName, String toEmail, String subject, String messageBody) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress(fromEmail, fromName));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject(subject);
			msg.setText(messageBody);
			Transport.send(msg);

		} catch (AddressException e) {

		} catch (MessagingException e) {

		}
	}
}