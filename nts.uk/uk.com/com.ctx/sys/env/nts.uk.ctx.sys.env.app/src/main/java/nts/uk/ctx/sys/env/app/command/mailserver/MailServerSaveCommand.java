/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailserver;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailserver.AuthenticationMethod;
import nts.uk.ctx.sys.env.dom.mailserver.EmailAuthentication;
import nts.uk.ctx.sys.env.dom.mailserver.EncryptionMethod;
import nts.uk.ctx.sys.env.dom.mailserver.ImapInfo;
import nts.uk.ctx.sys.env.dom.mailserver.IpVersion;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento;
import nts.uk.ctx.sys.env.dom.mailserver.Password;
import nts.uk.ctx.sys.env.dom.mailserver.PopInfo;
import nts.uk.ctx.sys.env.dom.mailserver.Port;
import nts.uk.ctx.sys.env.dom.mailserver.Server;
import nts.uk.ctx.sys.env.dom.mailserver.SmtpInfo;
import nts.uk.ctx.sys.env.dom.mailserver.TimeOut;
import nts.uk.ctx.sys.env.dom.mailserver.UseAuthentication;
import nts.uk.ctx.sys.env.dom.mailserver.UseServer;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailServerSaveCommand.
 */
@Data
public class MailServerSaveCommand implements MailServerGetMemento {
	
	/** The use auth. */
	private int useAuth;
	
	/** The encryption method. */
	private int encryptionMethod;
	
	/** The authentication method. */
	private int authenticationMethod;
	
	/** The email authencation. */
	private String emailAuthencation;
	
	/** The password. */
	private String password;
	
	/** The smtp dto. */
	private SmtpInfoDto smtpDto;
	
	/** The pop dto. */
	private PopInfoDto popDto;
	
	/** The imap dto. */
	private ImapInfoDto imapDto;
	
	/**
	 * Instantiates a new mail server save command.
	 */
	public MailServerSaveCommand(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return new String(AppContexts.user().companyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getUseAuthentication()
	 */
	@Override
	public UseAuthentication getUseAuthentication() {
		return UseAuthentication.valueOf(this.useAuth);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getEncryptionMethod()
	 */
	@Override
	public EncryptionMethod getEncryptionMethod() {
		return EncryptionMethod.valueOf(this.encryptionMethod);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getAuthenticationMethod()
	 */
	@Override
	public AuthenticationMethod getAuthenticationMethod() {
		return AuthenticationMethod.valueOf(this.authenticationMethod);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getEmailAuthentication()
	 */
	@Override
	public EmailAuthentication getEmailAuthentication() {
		return new EmailAuthentication(this.emailAuthencation);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getPassword()
	 */
	@Override
	public Password getPassword() {
		return new Password(this.password);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getSmtpInfo()
	 */
	@Override
	public SmtpInfo getSmtpInfo() {
		Server stmpServer = new Server(this.smtpDto.getSmtpServer());
		TimeOut stmpTimeOut = new TimeOut(this.smtpDto.getSmtpTimeOut());
		Port smtpPort = new Port(this.smtpDto.getSmtpPort());
		return new SmtpInfo(IpVersion.valueOf(this.smtpDto.smtpIpVersion), stmpServer, stmpTimeOut, smtpPort );
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getImapInfo()
	 */
	@Override
	public ImapInfo getImapInfo() {
		Server imapServer = new Server(this.imapDto.getImapServer());
		TimeOut imapTimeOut = new TimeOut(this.imapDto.getImapTimeOut());
		Port imapPort = new Port(this.imapDto.getImapPort());
		return new ImapInfo(IpVersion.valueOf(this.imapDto.getImapIpVersion()), imapServer, UseServer.valueOf(this.imapDto.getImapUseServer()) , imapTimeOut, imapPort);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailserver.MailServerGetMemento#getPopInfo()
	 */
	@Override
	public PopInfo getPopInfo() {
		Server popServer = new Server(this.popDto.getPopServer());
		TimeOut popTimeOut = new TimeOut(this.popDto.getPopTimeOut());
		Port popPort = new Port(this.popDto.getPopPort());
		return new PopInfo(IpVersion.valueOf(this.popDto.getPopIpVersion()), popServer, UseServer.valueOf(this.popDto.getPopUseServer()) , popTimeOut, popPort);
	}
	
}
