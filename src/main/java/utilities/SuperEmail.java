package utilities;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class SuperEmail {

//	Reads the txt file and returns as String
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

//	send email using Html Tags (uses org.apache.commons.mail.HtmlEmail)
	public static void sendEmailUsingHtmlMsg(String from, String to, String subject, String message) throws Exception {
		// Create an HtmlEmail object
		HtmlEmail email = new HtmlEmail();
		String host = ExcelRead.getData(17, 1, "variables");
		int port = Integer.parseInt(ExcelRead.getData(18, 1, "variables"));
		String username = ExcelRead.getData(19, 1, "variables");
		String password = ExcelRead.getData(20, 1, "variables");
		// Set the email properties
		email.setHostName(host);
		email.setSmtpPort(port);
		email.setAuthentication(username, password);
		email.setStartTLSEnabled(true);

		// Set the email content
		email.setFrom(from);
		email.addTo(to);
		email.setSubject(subject);

		email.setMsg(message);

		// Send the email
		email.send();
	}

//	send email using Html Tags (uses org.apache.commons.mail.HtmlEmail)
	public static void sendEmailUsingHtmlMsgFile(String from, String to, String subject, String attachmentPath) throws Exception {
		// Create an HtmlEmail object
		HtmlEmail email = new HtmlEmail();

		// Set the email properties
		email.setHostName("smtp.office365.com");
		email.setSmtpPort(587);
		email.setAuthentication("yaman.maharjan@javra.com", "!wertyu$@AQW");
		email.setStartTLSEnabled(true);

		// Set the email content
		email.setFrom(from);
		email.addTo(to);
		email.setSubject(subject);

//	      set the message from the txt file 
//	      use for ILS, PROCART and HtmlMsg
		String message = readFileAsString(attachmentPath);

		email.setMsg(message);

//	      email.setMsg works for both normal text content and HTML content
//	      email.setHtmlMsg(content);

		// Send the email
		email.send();
	}

	public static void sendEmailWithAttachment(String from, String to, String subject, String message, String attachmentPath) {
		try {
			// Create an HtmlEmail object
			HtmlEmail email = new HtmlEmail();

			// Set the email properties
			email.setHostName("smtp.office365.com");
			email.setSmtpPort(587);
			email.setAuthentication("yaman.maharjan@javra.com", "!wertyu$@AQW");
			email.setStartTLSEnabled(true);

			// Set the email content
			email.setFrom(from);
			email.addTo(to);
			email.setSubject(subject);
			email.setMsg(message);

			// Create an EmailAttachment object and add it to the email
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachmentPath);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Attachment");
			attachment.setName("attachment.txt");
			email.attach(attachment);

			// Send the email
			email.send();
			System.out.println("Email sent successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
