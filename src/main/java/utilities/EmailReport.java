package utilities;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Send automated email
 * 
 * @author sanish
 *
 */
public class EmailReport {

	private BodyPart messageBodyPart = new MimeBodyPart();
	private MimeBodyPart attachment = new MimeBodyPart();
	private Multipart multipart = new MimeMultipart();
	private Properties props = new Properties();
	private Session session;
	private MimeMessage message;

	private String from, to, cc, defaultHost = "smtp.wlink.com.np";
	private String messageBody = "Dear All,\n\nPlease find attached automation report for today.",
			msgEnd = "\n\nNote: This is an automatically generated email. Please do not reply.\n", fileToAttach;

	private boolean check = true;

	/**
	 * Adds failed cases to message body
	 * 
	 * @param messageBody
	 *            the messageBody to set
	 */
	public void setMessageBody(String messageBody)
	{
		if (check)
		{
			this.messageBody += "\n\nFollwing issues have been found so far:\n";
			check = false;
		}
		this.messageBody += "\n" + messageBody;

		// please add this method in the automation script
		// @AfterMethod
		// public void getFailedCases() {
		// String msgToWrite = "";
		// if (!test.getRunStatus().equals(LogStatus.PASS)) {
		// msgToWrite = test.getTest().getStatus() + " : " + test.getTest().getName();
		// er.setMessageBody(msgToWrite);
		// }
		// }
	}

	/**
	 * Sets the location of attachment for email
	 * 
	 * @param fileToAttach
	 *            the fileToAttach to set
	 */
	public void setFileToAttach(String fileToAttach) {
		this.fileToAttach = fileToAttach;

		// use this method as
		// er.setFileToAttach(ZipFiles.zip(fileName));
	}

	/**
	 * Initialises required variable instances
	 */
	private void init() {

		from = ExcelRead.getData(10, 1, 0);
		to = ExcelRead.getData(12, 1, 0);
		cc = ExcelRead.getData(13, 1, 0);
		msgEnd += "\nThank you!";
		props.put("mail.smtp.host", defaultHost);
		session = Session.getDefaultInstance(props);
	}

	/**
	 * Creates mail using given details
	 */
	private void createMail() {
		try {
			// Create a default MimeMessage object.
			message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set To: header field of the header.
			if (cc.contains(";"))
			{
				String[] recipients = cc.split(";");
				for (String recipient : recipients)
				{
					if (!recipient.trim().equals(""))
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
				}
			}
			else
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			// Set Subject: header field
			if (messageBody.contains("Open GBS URL"))
			{
				message.setSubject("Urgent: GBS Production system is down");
			}
			else
			{
				message.setSubject("GIP smoke test report");
			}

			// Fill the message
			messageBodyPart.setText(messageBody + msgEnd);

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			File attach = new File(fileToAttach);
			DataSource source = new FileDataSource(attach);
			attachment.setDataHandler(new DataHandler(source));
			attachment.setFileName(attach.getName());
			multipart.addBodyPart(attachment);

			// Send the complete message parts
			message.setContent(multipart);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	 * Sends mail to given recipient(s) from given sender
	 */
	public void sendEmail() {
		init();
		createMail();

		// Send message
		try {
			// if "javax.mail.AuthenticationFailedException: failed to connect" exception
			// occurs, go to https://myaccount.google.com/lesssecureapps and turn on "Allow
			// less secure apps"

			Transport.send(message);
			System.err.println("Email sent successfully!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}