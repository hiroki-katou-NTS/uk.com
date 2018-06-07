/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.ac.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.mail.send.setting.SendMailAuthenticationAccount;
import nts.gul.mail.send.setting.SendMailAuthenticationMethod;
import nts.gul.mail.send.setting.SendMailEncryptedConnectionType;
import nts.gul.mail.send.setting.SendMailSetting;
import nts.gul.misc.ServerLocator;
import nts.uk.ctx.sys.env.dom.mailserver.AuthenticationMethod;
import nts.uk.ctx.sys.env.dom.mailserver.EncryptionMethod;
import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;
import nts.uk.ctx.sys.env.dom.mailserver.UseAuthentication;
import nts.uk.shr.com.mail.SendMailSettingAdaptor;
import nts.uk.shr.com.mail.UkSendMailSetting;

/**
 * The Class SendMailSettingAdaptorImpl.
 */
@Stateless
public class SendMailSettingAdaptorImpl implements SendMailSettingAdaptor {

	/** The mail server repo. */
	@Inject
	private MailServerRepository mailServerRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.com.mail.SendMailSettingAdaptor#getSetting(java.lang.String)
	 */
	@Override
	public UkSendMailSetting getSetting(String companyId) {
		Optional<MailServer> optMailServer = mailServerRepo.findBy(companyId);

		// Check exist
		if (!optMailServer.isPresent()) {
			return null;
		}

		// Get info
		MailServer mailServer = optMailServer.get();

		// Return data
		ServerLocator smtpServer = new ServerLocator(mailServer.getSmtpInfo().getServer().v(),
				mailServer.getSmtpInfo().getPort().v());
		int secondsToTimeout = 60;
		SendMailAuthenticationMethod authenticationMethod = SendMailAuthenticationMethod.NONE;
		if (UseAuthentication.USE.equals(mailServer.getUseAuthentication())) {
			authenticationMethod = this
					.switchAuthenticationMethodEnum(mailServer.getAuthenticationMethod());
		}

		Optional<SendMailAuthenticationAccount> authenticationAccount = Optional
				.of(new SendMailAuthenticationAccount(mailServer.getEmailAuthentication().v(),
						mailServer.getPassword().v()));
		Optional<SendMailEncryptedConnectionType> encryptionType = Optional
				.of(this.switchEncryptionTypeEnum(mailServer.getEncryptionMethod()));
		ServerLocator serverLocator = null;

		// Get info
		switch (authenticationMethod) {
		case POP_BEFORE_SMTP:
			serverLocator = new ServerLocator(mailServer.getPopInfo().getServer().v(),
					mailServer.getPopInfo().getPort().v());
			break;

		case IMAP_BEFORE_SMTP:
			serverLocator = new ServerLocator(mailServer.getImapInfo().getServer().v(),
					mailServer.getImapInfo().getPort().v());
			break;

		default:
			serverLocator = new ServerLocator(mailServer.getSmtpInfo().getServer().v(),
					mailServer.getSmtpInfo().getPort().v());
			break;
		}

		Optional<ServerLocator> authenticationServer = Optional.of(serverLocator);
		SendMailSetting sendMailSetting = new SendMailSetting(smtpServer, secondsToTimeout,
				authenticationMethod, authenticationAccount, authenticationServer, encryptionType);

		return new UkSendMailSetting(sendMailSetting, mailServer.getEmailAuthentication().v());
	}

	/**
	 * Switch authentication method enum.
	 *
	 * @param authenticationMethod
	 *            the authentication method
	 * @return the send mail authentication method
	 */
	private SendMailAuthenticationMethod switchAuthenticationMethodEnum(
			AuthenticationMethod authenticationMethod) {
		switch (authenticationMethod) {
		case POP_BEFORE_SMTP:
			return SendMailAuthenticationMethod.POP_BEFORE_SMTP;
		case IMAP_BEFORE_SMTP:
			return SendMailAuthenticationMethod.IMAP_BEFORE_SMTP;
		case SMTP_AUTH_LOGIN:
			return SendMailAuthenticationMethod.SMTP_AUTH_LOGIN;
		case SMTP_AUTH_PLAIN:
			return SendMailAuthenticationMethod.SMTP_AUTH_PLAIN;
		case SMTP_AUTH_CRAM_MD5:
			return SendMailAuthenticationMethod.SMTP_AUTH_MD5;
		default:
			return SendMailAuthenticationMethod.NONE;
		}
	}

	/**
	 * Switch encryption type enum.
	 *
	 * @param encryptionMethod
	 *            the encryption method
	 * @return the send mail encrypted connection type
	 */
	private SendMailEncryptedConnectionType switchEncryptionTypeEnum(
			EncryptionMethod encryptionMethod) {
		switch (encryptionMethod) {
		case TSL:
			return SendMailEncryptedConnectionType.TLS;
		case SSL:
			return SendMailEncryptedConnectionType.SSL;
		default:
			return SendMailEncryptedConnectionType.NONE;
		}
	}
}
