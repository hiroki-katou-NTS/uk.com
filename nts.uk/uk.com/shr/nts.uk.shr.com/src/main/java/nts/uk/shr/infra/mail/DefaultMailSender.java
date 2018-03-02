package nts.uk.shr.infra.mail;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailOriginator;
import nts.gul.mail.send.MailRecipient;
import nts.gul.mail.send.exceptions.FailedAuthenticateException;
import nts.gul.mail.send.exceptions.FailedConnectAuthServerException;
import nts.gul.mail.send.exceptions.FailedConnectSmtpServerException;
import nts.gul.mail.send.strategy.MailerFactory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.mail.SendMailSettingAdaptor;

@Stateless
public class DefaultMailSender implements MailSender {

	@Inject
	private SendMailSettingAdaptor settingAdaptor;
	
	@Override
	public void send(MailOriginator from, MailRecipient to, MailContents contents)
			throws SendMailFailedException {
		
		String companyId = AppContexts.user().companyId();
		val setting = this.settingAdaptor.getSetting(companyId);
		
		if (setting == null) {
			throw SendMailFailedException.asNoSetting();
		}
		
		val mailer = MailerFactory.create(setting);
		
		try {
			mailer.send(new DefaultMailToSend(from, to, contents));
		} catch (FailedConnectSmtpServerException e) {
			throw SendMailFailedException.asCannotConnectSmtpServer();
		} catch (FailedConnectAuthServerException e) {
			throw SendMailFailedException.asCannotConnectAuthServer();
		} catch (FailedAuthenticateException e) {
			throw SendMailFailedException.asAuthenticationFailed();
		}
	}
	
	
}