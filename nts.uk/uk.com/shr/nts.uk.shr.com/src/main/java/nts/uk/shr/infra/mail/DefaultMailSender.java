package nts.uk.shr.infra.mail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.val;
import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailOriginator;
import nts.gul.mail.send.MailRecipient;
import nts.gul.mail.send.exceptions.FailedAuthenticateException;
import nts.gul.mail.send.exceptions.FailedConnectAuthServerException;
import nts.gul.mail.send.exceptions.FailedConnectSmtpServerException;
import nts.gul.mail.send.setting.SendMailSetting;
import nts.gul.mail.send.strategy.MailerFactory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.EmailAddressForSender;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.mail.SendMailSettingAdaptor;
import nts.uk.shr.com.mail.UkSendMailSetting;

@RequestScoped
public class DefaultMailSender implements MailSender {

	@Inject
	private SendMailSettingAdaptor settingAdaptor;
	
	@Override
	public void send(MailOriginator from, MailRecipient to, MailContents contents)
			throws SendMailFailedException {
		
		val setting = this.loadSetting();
		sendInternal(from, to, contents, setting.getBasics());
	}

	@Override
	public void sendFromAdmin(MailRecipient to, MailContents contents) throws SendMailFailedException {
		
		val setting = this.loadSetting();
		val from = new EmailAddressForSender(setting.getMailAddressAdmin());
		sendInternal(from, to, contents, setting.getBasics());
	}

	private UkSendMailSetting loadSetting() {
		String companyId = AppContexts.user().companyId();
		val setting = this.settingAdaptor.getSetting(companyId);
		
		if (setting == null) {
			throw SendMailFailedException.asNoSetting();
		}
		return setting;
	}

	private static void sendInternal(
			MailOriginator from,
			MailRecipient to,
			MailContents contents,
			SendMailSetting setting) {
		
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