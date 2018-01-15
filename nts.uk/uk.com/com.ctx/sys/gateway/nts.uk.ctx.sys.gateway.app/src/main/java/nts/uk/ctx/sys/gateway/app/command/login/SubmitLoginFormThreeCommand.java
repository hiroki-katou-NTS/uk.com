/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login;

/**
 * The Class SubmitLoginFormThreeCommand.
 */
public class SubmitLoginFormThreeCommand {

	/** The company code. */
	private String companyCode;

	/** The employee code. */
	private String employeeCode;

	/** The password. */
	private String password;

	/** The contract code. */
	private String contractCode;

	/** The contract password. */
	private String contractPassword;

	/**
	 * Instantiates a new submit login form three command.
	 */
	public SubmitLoginFormThreeCommand() {
		super();
	}

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	public String getCompanyCode() {
		return companyCode.trim();
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode
	 *            the new company code
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode.trim();
	}

	/**
	 * Gets the employee code.
	 *
	 * @return the employee code
	 */
	public String getEmployeeCode() {
		return employeeCode.trim();
	}

	/**
	 * Sets the employee code.
	 *
	 * @param employeeCode
	 *            the new employee code
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode.trim();
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
