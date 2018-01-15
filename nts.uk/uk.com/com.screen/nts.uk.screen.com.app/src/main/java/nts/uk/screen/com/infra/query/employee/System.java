/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.employee;


/**
 * The Enum System.
 */
public enum System {
	
	/** The Employment. */
	Employment("emp"),
	
	/** The Human resources. */
	Human_Resources("hrm"),
	
	/** The Salary. */
	Salary("sal"),
	
	/** The Accounting. */
	Accounting("acc");
	
	/** The code. */
	public String code;

	/**
	 * Instantiates a new system.
	 *
	 * @param code the code
	 */
	private System(String code) {
		this.code = code;
	}
	
	/**
	 * Value of code.
	 *
	 * @param code the code
	 * @return the system
	 */
	public static System valueOfCode(String code) {
		// Invalid object.
		if (code == null) {
			return null;
		}

		// Find value.
		for (System val : System.values()) {
			if (val.code.equals(code)) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
