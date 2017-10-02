package nts.uk.ctx.sys.gateway.dom.mailserver;

import nts.uk.ctx.sys.gateway.dom.common.CompanyId;

public interface MailServerGetMemento {
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets use authentication
	 * 
	 * @return value
	 */
	UseAuthentication getUseAuthentication();

	/**
	 * Gets Encryption Method
	 * 
	 * @return Encryption Method
	 */
	EncryptionMethod getEncryptionMethod();

	/**
	 * Gets Authentication Method
	 * 
	 * @return Authentication Method
	 */
	AuthenticationMethod getAuthenticationMethod();

	/**
	 * Gets Email Authentication
	 * 
	 * @return Email Authentication
	 */
	EmailAuthentication getEmailAuthentication();

	/**
	 * Gets password
	 * 
	 * @return password
	 */
	Password getPassword();
	
	/**
	 * Gets SMTP Information
	 * 
	 * @return SMTP Information
	 */
	SmtpInfo getSmtpInfo();
	
	/**
	 * Gets IMAP Information
	 * 
	 * @return password
	 */
	ImapInfo getImapInfo();
	
	/**
	 * Gets POP Information
	 * 
	 * @return POP Information
	 */
	PopInfo getPopInfo();
}
