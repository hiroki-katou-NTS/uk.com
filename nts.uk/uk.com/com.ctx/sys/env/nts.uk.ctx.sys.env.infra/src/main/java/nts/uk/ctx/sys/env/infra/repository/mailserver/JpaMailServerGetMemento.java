/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailserver;

import lombok.Setter;
import nts.uk.ctx.sys.env.dom.mailserver.AuthenticationMethod;
import nts.uk.ctx.sys.env.dom.mailserver.EmailAuthentication;
import nts.uk.ctx.sys.env.dom.mailserver.EncryptionMethod;
import nts.uk.ctx.sys.env.dom.mailserver.ImapInfo;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento;
import nts.uk.ctx.sys.env.dom.mailserver.Password;
import nts.uk.ctx.sys.env.dom.mailserver.PopInfo;
import nts.uk.ctx.sys.env.dom.mailserver.Port;
import nts.uk.ctx.sys.env.dom.mailserver.Server;
import nts.uk.ctx.sys.env.dom.mailserver.SmtpInfo;
import nts.uk.ctx.sys.env.dom.mailserver.UseAuthentication;
import nts.uk.ctx.sys.env.dom.mailserver.UseServer;
import nts.uk.ctx.sys.env.infra.entity.mailserver.SevmtMailServer;

/**
 * The Class JpaMailServerGetMemento.
 */
public class JpaMailServerGetMemento implements MailServerGetMemento {
	
	/**
	 * Sets the sevst mail server.
	 *
	 * @param sevmtMailServer the new sevst mail server
	 */
	@Setter
	private SevmtMailServer sevmtMailServer;
	
	/**
	 * Instantiates a new jpa mail server get memento.
	 *
	 * @param sevmtMailServer the sevst mail server
	 */
	public JpaMailServerGetMemento(SevmtMailServer sevmtMailServer) {
		this.sevmtMailServer = sevmtMailServer;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return new String(this.sevmtMailServer.getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getUseAuthentication()
	 */
	@Override
	public UseAuthentication getUseAuthentication() {
		return UseAuthentication.valueOf((int) this.sevmtMailServer.getUseAuth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getEncryptionMethod()
	 */
	@Override
	public EncryptionMethod getEncryptionMethod() {
		return EncryptionMethod.valueOf((int) this.sevmtMailServer.getEncryptMethod());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getAuthenticationMethod()
	 */
	@Override
	public AuthenticationMethod getAuthenticationMethod() {
		return AuthenticationMethod.valueOf((int) this.sevmtMailServer.getAuthMethod());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getEmailAuthentication()
	 */
	@Override
	public EmailAuthentication getEmailAuthentication() {
		return new EmailAuthentication(this.sevmtMailServer.getEmailAuth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getPassword()
	 */
	@Override
	public Password getPassword() {
		return new Password(this.sevmtMailServer.getPassword());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getSmtpInfo()
	 */
	@Override
	public SmtpInfo getSmtpInfo() {
		return new SmtpInfo(new Server(this.sevmtMailServer.getSmtpServer()),  
						new Port(this.sevmtMailServer.getSmtpPort()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getImapInfo()
	 */
	@Override
	public ImapInfo getImapInfo() {
		return new ImapInfo(new Server(this.sevmtMailServer.getImapServer()),
				UseServer.valueOf((int)this.sevmtMailServer.getImapUse()),
				new Port(this.sevmtMailServer.getImapPort()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getPopInfo()
	 */
	@Override
	public PopInfo getPopInfo() {
		return new PopInfo(new Server(this.sevmtMailServer.getPopServer()),
				UseServer.valueOf((int)this.sevmtMailServer.getPopUse()),
				new Port(this.sevmtMailServer.getPopPort()));
	}
	
}
