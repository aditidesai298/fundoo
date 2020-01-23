package com.bridgelabz.fundoo.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	private static final String SENDER_EMAIL_ID = System.getenv("email");
	private static final String SENDER_PASSWORD = System.getenv("password");

	/**
	 * This function takes following input parameter and configure the
	 * authentication of smtp and port 587 and authorisation and send the mail to
	 * the assigned user details.
	 * 
	 * @param toEmailId
	 * @param subject
	 * @param bodyContaint
	 */
	public void sendMail(String toEmailId, String subject, String bodyContaint) {
		Authenticator authentication = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL_ID, SENDER_PASSWORD);
			}
		};
		Session session = Session.getInstance(mailPropertiesSettings(), authentication);
		try {
			Transport.send(mimeMessageConfiguration(session, toEmailId, subject, bodyContaint));
		} catch (MessagingException e) {
			e.printStackTrace();

		}

	}

	/**
	 * This function takes following information and sets all the header information
	 * of the email.
	 * 
	 * @param session as Current session information
	 * @param toEmail as String receiver's mail id
	 * @param subject as String input parameter
	 * @param body    as String input parameter
	 * @return MimeMessage class
	 * @throws MessagingException 
	 */
	private MimeMessage mimeMessageConfiguration(Session session, String toEmail, String subject, String body) throws MessagingException {

		MimeMessage mimeMessage = new MimeMessage(session);
		// set message headers
		
			try {
				mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.addHeader("format", "flowed");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.setFrom(new InternetAddress(SENDER_EMAIL_ID, "Fundoo Note Application"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.setReplyTo(InternetAddress.parse(SENDER_EMAIL_ID, false));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.setSubject(subject, "UTF-8");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				mimeMessage.setText(body, "UTF-8");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mimeMessage.setSentDate(new Date());
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		
		return mimeMessage;
	}

	/**
	 * This class sets the properties configuaration of the mail and return it.
	 * 
	 * @return Properties class
	 */
	private Properties mailPropertiesSettings() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		return properties;

	}
}