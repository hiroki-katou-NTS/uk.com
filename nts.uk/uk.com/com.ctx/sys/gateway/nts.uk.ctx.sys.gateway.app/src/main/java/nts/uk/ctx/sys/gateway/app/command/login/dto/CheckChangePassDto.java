/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login.dto;

/**
 * The Class CheckChangePassDto.
 */
public class CheckChangePassDto {

	/** The show change pass. */
	public boolean showChangePass;
	
	/** The msg error id. */
	public String msgErrorId;

	/** The show contract. */
	public boolean showContract;
	
	public CheckChangePassDto() {
		super();
	}

	/**
	 * Instantiates a new check change pass dto.
	 *
	 * @param showChangePass the show change pass
	 */
	public CheckChangePassDto(boolean showChangePass, String msgErrorId, boolean showContract) {
		super();
		this.showChangePass = showChangePass;
		this.msgErrorId = msgErrorId;
		this.showContract = showContract;
	}
}
