package nts.uk.shr.com.mail;

import lombok.val;
import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailOriginator;
import nts.gul.mail.send.MailRecipient;

/**
 * Injectable interface to send mail
 */
public interface MailSender {
	
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
		
		val from = new MailOriginator() {
			@Override
			public String mailAddress() { return fromMailAddres; }
		};
		
		val to = new MailRecipient() {
			@Override
			public String mailAddress() { return toMailAddress; }
		};
		
		this.send(from, to, contents);
	}
}
