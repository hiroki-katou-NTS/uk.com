package nts.uk.shr.infra.mail;

import lombok.Value;
import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailOriginator;
import nts.gul.mail.send.MailRecipient;
import nts.gul.mail.send.MailToSend;

@Value
public class DefaultMailToSend implements MailToSend {

	private final MailOriginator from;
	private final MailRecipient to;
	private final MailContents contents;

	@Override
	public MailOriginator originator() {
		return this.from;
	}

	@Override
	public MailRecipient recipient() {
		return this.to;
	}

	@Override
	public MailContents contents() {
		return this.contents;
	}
}
