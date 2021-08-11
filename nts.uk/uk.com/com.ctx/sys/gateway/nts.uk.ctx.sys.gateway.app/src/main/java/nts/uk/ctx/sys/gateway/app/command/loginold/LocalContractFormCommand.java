/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.loginold;

/**
 * The Class LocalContractFormCommand.
 */

public class LocalContractFormCommand {

	/** The contract code. */
	private String contractCode;

	/** The contract password. */
	private String contractPassword;

	/**
	 * Instantiates a new local contract form command.
	 */
	public LocalContractFormCommand() {
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
