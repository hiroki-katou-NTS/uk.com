/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

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
}
