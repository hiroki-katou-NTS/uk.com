/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailserver;

import lombok.Data;

/**
 * The Class ImapInfoDto.
 */
@Data
public class ImapInfoDto {
	
	/** The imap ip version. */
	public int imapIpVersion;
	
	/** The imap server. */
	public String imapServer;
	
	/** The imap use server. */
	public int imapUseServer;
	
	/** The imap time out. */
	public int imapTimeOut;
	
	/** The imap port. */
	public int imapPort;
	
	/**
	 * Instantiates a new imap info dto.
	 *
	 * @param imapIpVersions the imap ip versions
	 * @param imapServer the imap server
	 * @param imapUseServer the imap use server
	 * @param imapTimeOut the imap time out
	 * @param imapPort the imap port
	 */
	public ImapInfoDto(int imapIpVersions, String imapServer, int imapUseServer, int imapTimeOut, int imapPort){
		this.imapIpVersion = imapIpVersions;
		this.imapServer = imapServer;
		this.imapUseServer = imapUseServer;
		this.imapTimeOut = imapTimeOut;
		this.imapPort = imapPort;
	}
	
	/**
	 * Instantiates a new imap info dto.
	 */
	public ImapInfoDto() {
		super();
	}
}
