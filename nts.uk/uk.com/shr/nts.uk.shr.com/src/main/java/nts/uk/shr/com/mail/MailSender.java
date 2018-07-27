package nts.uk.shr.com.mail;

import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailOriginator;
import nts.gul.mail.send.MailRecipient;

/**
 * Injectable interface to send mail
 */
public interface MailSender {
	
	/**
	 * Send mail. FROM is MailServer.emailAuthentication.
	 * @param to to
	 * @param contents contents
	 * @throws SendMailFailedException thrown when any problem occurs about connect mail server
	 */
	void sendFromAdmin(MailRecipient to, MailContents contents)
			throws SendMailFailedException;
	
	/**
	 * Send mail. FROM is MailServer.emailAuthentication.
	 * @param toMailAddress to
	 * @param contents contents
	 * @throws SendMailFailedException thrown when any problem occurs about connect mail server
	 */
	default void sendFromAdmin(String toMailAddress, MailContents contents) {
		this.sendFromAdmin(new EmailAddressForSender(toMailAddress), contents);
	}
	
	/**
	 * Send mail.
	 * @param from from
	 * @param to to
	 * @param contents contents
	 * @throws SendMailFailedException thrown when any problem occurs about connect mail server
	 */
	void send(MailOriginator from, MailRecipient to, MailContents contents)
			throws SendMailFailedException;

	/**
	 * Send mail.
	 * @param fromMailAddres from
	 * @param toMailAddress to
	 * @param contents contents
	 * @throws SendMailFailedException thrown when any problem occurs about connect mail server
	 */
	default void send(String fromMailAddres, String toMailAddress, MailContents contents)
			throws SendMailFailedException {
		this.send(
				new EmailAddressForSender(fromMailAddres),
				new EmailAddressForSender(toMailAddress),
				contents);
	}
}
