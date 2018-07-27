/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class SubmitLoginFormOneCommand.
 */
public class SubmitLoginFormOneCommand {

	/** The login id. */
	private String loginId;

	/** The password. */
	private String password;

	/** The contract code. */
	private String contractCode;

	/** The contract password. */
	private String contractPassword;

	/** The is sign on. */
	private boolean signOn;
	
	/** The request. */
	private HttpServletRequest request;
	
	/**
	 * Instantiates a new submit login form one command.
	 */
	public SubmitLoginFormOneCommand() {
		super();
	}

	/**
	 * Gets the login id.
	 *
	 * @return the login id
	 */
	public String getLoginId() {
		return loginId.trim();
	}

	/**
	 * Sets the login id.
	 *
	 * @param loginId
	 *            the new login id
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId.trim();
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the contract code.
	 *
	 * @return the contract code
	 */
	public String getContractCode() {
		return contractCode.trim();
	}

	/**
	 * Sets the contract code.
	 *
	 * @param contractCode
	 *            the new contract code
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode.trim();
	}

	/**
	 * Gets the contract password.
	 *
	 * @return the contract password
	 */
	public String getContractPassword() {
		return contractPassword;
	}

	/**
	 * Sets the contract password.
	 *
	 * @param contractPassword
	 *            the new contract password
	 */
	public void setContractPassword(String contractPassword) {
		this.contractPassword = contractPassword;
	}
	
	/**
	 * Checks if is sign on.
	 *
	 * @return true, if is sign on
	 */
	public boolean isSignOn() {
        return signOn;
    }

    /**
     * Sets the sign on.
     *
     * @param signOn the new sign on
     */
    public void setSignOn(boolean signOn) {
        this.signOn = signOn;
    }

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
