/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailserver;

import nts.uk.ctx.sys.env.dom.mailserver.AuthenticationMethod;
import nts.uk.ctx.sys.env.dom.mailserver.EmailAuthentication;
import nts.uk.ctx.sys.env.dom.mailserver.EncryptionMethod;
import nts.uk.ctx.sys.env.dom.mailserver.ImapInfo;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento;
import nts.uk.ctx.sys.env.dom.mailserver.Password;
import nts.uk.ctx.sys.env.dom.mailserver.PopInfo;
import nts.uk.ctx.sys.env.dom.mailserver.SmtpInfo;
import nts.uk.ctx.sys.env.dom.mailserver.UseAuthentication;
import nts.uk.ctx.sys.env.infra.entity.mailserver.SevmtMailServer;

/**
 * The Class JpaMailServerSetMemento.
 */
public class JpaMailServerSetMemento implements MailServerSetMemento {
	
	/** The sevst mail server. */
	private SevmtMailServer sevmtMailServer;

	/**
	 * Instantiates a new jpa mail server set memento.
	 *
	 * @param sevmtMailServer
	 *            the sevst mail server
	 */
	public JpaMailServerSetMemento(SevmtMailServer sevmtMailServer) {
		this.sevmtMailServer = sevmtMailServer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#setCompanyId(java.
	 * lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.sevmtMailServer.setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#
	 * setUseAuthentication(nts.uk.ctx.sys.env.dom.mailserver.UseAuthentication)
	 */
	@Override
	public void setUseAuthentication(UseAuthentication useAuthentication) {
		this.sevmtMailServer.setUseAuth((short) useAuthentication.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#
	 * setEncryptionMethod(nts.uk.ctx.sys.env.dom.mailserver.EncryptionMethod)
	 */
	@Override
	public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
		this.sevmtMailServer.setEncryptMethod((short) encryptionMethod.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#
	 * setAuthenticationMethod(nts.uk.ctx.sys.env.dom.mailserver.
	 * AuthenticationMethod)
	 */
	@Override
	public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
		this.sevmtMailServer.setAuthMethod((short) authenticationMethod.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#
	 * setEmailAuthentication(nts.uk.ctx.sys.env.dom.mailserver.
	 * EmailAuthentication)
	 */
	@Override
	public void setEmailAuthentication(EmailAuthentication emailAuthentication) {
		this.sevmtMailServer.setEmailAuth(emailAuthentication.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#setPassword(nts.uk
	 * .ctx.sys.env.dom.mailserver.Password)
	 */
	@Override
	public void setPassword(Password password) {
		this.sevmtMailServer.setPassword(password.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#setSmtpInfo(nts.uk
	 * .ctx.sys.env.dom.mailserver.SmtpInfo)
	 */
	@Override
	public void setSmtpInfo(SmtpInfo smtpInfo) {
		this.sevmtMailServer.setSmtpServer(smtpInfo.getServer().v());
		this.sevmtMailServer.setSmtpPort(smtpInfo.getPort().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#setImapInfo(nts.uk
	 * .ctx.sys.env.dom.mailserver.ImapInfo)
	 */
	@Override
	public void setImapInfo(ImapInfo imapInfo) {
		this.sevmtMailServer.setImapServer(imapInfo.getServer().v());
		this.sevmtMailServer.setImapUse((short) imapInfo.getUseServer().value);
		this.sevmtMailServer.setImapPort(imapInfo.getPort().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerSetMemento#setPopInfo(nts.uk.
	 * ctx.sys.env.dom.mailserver.PopInfo)
	 */
	@Override
	public void setPopInfo(PopInfo popInfo) {
		this.sevmtMailServer.setPopServer(popInfo.getServer().v());
		this.sevmtMailServer.setPopUse((short) popInfo.getUseServer().value);
		this.sevmtMailServer.setPopPort(popInfo.getPort().v());
	}

}
