/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login.dto;

/**
 * The Class LoginRecordInfor.
 */
public class LoginRecordInfor {
	
	/** The program id. */
	public String programId;
	
	/** The screen id. */
	public String screenId;
	
	/** The query param. */
	public String queryParam;
	
	/** The login status. */
	public Integer loginStatus;
	
	/** The login method. */
	public Integer loginMethod;
	
	/** The url. */
	public String url;
	
	/** The remark. */
	public String remark;
	
	/**
	 * Instantiates a new login record infor.
	 *
	 * @param programId the program id
	 * @param screenId the screen id
	 * @param queryParam the query param
	 * @param loginStatus the login status
	 * @param loginMethod the login method
	 * @param url the url
	 * @param remark the remark
	 */
	public LoginRecordInfor(String programId, String screenId, String queryParam, Integer loginStatus, Integer loginMethod, String url, String remark){
		this.programId = programId;
		this.screenId = screenId;
		this.queryParam = queryParam;
		this.loginStatus = loginStatus;
		this.loginMethod = loginMethod;
		this.url = url;
		this.remark = remark;
	}
}
