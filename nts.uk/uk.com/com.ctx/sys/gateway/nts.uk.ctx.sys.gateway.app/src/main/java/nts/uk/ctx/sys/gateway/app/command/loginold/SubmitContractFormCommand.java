/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

/**
 * The Class SubmitContractFormCommand.
 */

public class SubmitContractFormCommand {

	/** The contract code. */
	private String contractCode;

	/** The password. */
	private String password;

	/**
	 * Instantiates a new submit contract form command.
	 */
	public SubmitContractFormCommand() {
		super();
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
}
