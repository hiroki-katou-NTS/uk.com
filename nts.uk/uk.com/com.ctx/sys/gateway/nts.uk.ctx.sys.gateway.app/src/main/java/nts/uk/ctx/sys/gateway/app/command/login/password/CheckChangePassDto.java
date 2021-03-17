/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login.password;

import lombok.Setter;

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
	
	/** param for SystemSuspend */
	@Setter
	public String successMsg;
	/**変更理由*/
	public String changePassReason;
	/**残り何日*/
	public int spanDays;
	
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
	public CheckChangePassDto(String changePassReason) {
		super();
		this.showChangePass = true;
		this.changePassReason = changePassReason;
	}
	public CheckChangePassDto(String msgErrorId, int spanDays) {
		super();
		this.msgErrorId = msgErrorId;
		this.spanDays = spanDays;
	}
	
	public static CheckChangePassDto failedToAuthTenant() {
		return new CheckChangePassDto(false, null, true);
	}

	public static CheckChangePassDto successToAuthPassword() {
		return new CheckChangePassDto(false, null, false);
	}
	
	public static CheckChangePassDto failedToAuthPassword() {
		return new CheckChangePassDto(false, "Msg_302", false);
	}
}
