/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailserver;

import lombok.Data;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
public class SmtpInfoDto {
	
	/** The smtp ip version. */
	public int smtpIpVersion;
	
	/** The smtp server. */
	public String smtpServer;
	
	/** The smtp time out. */
	public int smtpTimeOut;
	
	/** The smtp port. */
	public int smtpPort;
	
	/**
	 * Instantiates a new smtp info dto.
	 *
	 * @param smtpIpVersions the smtp ip versions
	 * @param smtpServer the smtp server
	 * @param smtpTimeOut the smtp time out
	 * @param smtpPort the smtp port
	 */
	public SmtpInfoDto(int smtpIpVersions, String smtpServer, int smtpTimeOut, int smtpPort){
		this.smtpIpVersion = smtpIpVersions;
		this.smtpServer = smtpServer;
		this.smtpTimeOut = smtpTimeOut;
		this.smtpPort = smtpPort;
	}

	public SmtpInfoDto() {
		super();
	}
}
