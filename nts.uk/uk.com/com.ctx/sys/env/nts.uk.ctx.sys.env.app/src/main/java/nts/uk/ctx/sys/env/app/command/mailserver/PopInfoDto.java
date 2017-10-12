/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailserver;

import lombok.Data;

/**
 * The Class PopInfoDto.
 */
@Data
public class PopInfoDto {
	
	/** The pop ip version. */
	public int popIpVersion;
	
	/** The pop server. */
	public String popServer;
	
	/** The pop use server. */
	public int popUseServer;
	
	/** The pop time out. */
	public int popTimeOut;
	
	/** The pop port. */
	public int popPort;
	
	/**
	 * Instantiates a new pop info dto.
	 *
	 * @param popIpVersions the pop ip versions
	 * @param popServer the pop server
	 * @param popUseServer the pop use server
	 * @param popTimeOut the pop time out
	 * @param popPort the pop port
	 */
	public PopInfoDto(int popIpVersions, String popServer, int popUseServer, int popTimeOut, int popPort){
		this.popIpVersion = popIpVersions;
		this.popServer = popServer;
		this.popUseServer = popUseServer;
		this.popTimeOut = popTimeOut;
		this.popPort = popPort;
	}
	
	/**
	 * Instantiates a new pop info dto.
	 */
	public PopInfoDto() {
		super();
	}
}
