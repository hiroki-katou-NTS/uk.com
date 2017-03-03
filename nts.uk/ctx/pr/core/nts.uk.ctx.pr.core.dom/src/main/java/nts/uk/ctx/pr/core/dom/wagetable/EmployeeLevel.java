/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.wagetable;

/**
 * The Enum EmployeeLevel.
 */
public enum EmployeeLevel {

	/** The level01. */
	LEVEL01("Level01"),

	/** The level02. */
	LEVEL02("Level02"),

	/** The level03. */
	LEVEL03("Level03"),

	/** The level04. */
	LEVEL04("Level04"),

	/** The level05. */
	LEVEL05("Level05");

	/** The value. */
	public String value;

	/** The Constant values. */
	private final static EmployeeLevel[] values = EmployeeLevel.values();

	/**
	 * Instantiates a new employee level.
	 *
	 * @param value
	 *            the value
	 * @param code
	 *            the code
	 */
	private EmployeeLevel(String value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the employee level
	 */
	public static EmployeeLevel valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EmployeeLevel val : EmployeeLevel.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
