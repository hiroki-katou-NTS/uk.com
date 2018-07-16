/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

/**
 * The Enum SystemType.
 */
public enum CCG001SystemType {

	/** The personal information. */
	// システム管理者
	PERSONAL_INFORMATION(1),

	/** The employment. */
	// 就業
	EMPLOYMENT(2),

	/** The salary. */
	// 給与
	SALARY(3),

	/** The human resources. */
	// 人事
	HUMAN_RESOURCES(4),

	/** The administrator. */
	// 管理者
	ADMINISTRATOR(5);

	/** The value. */
	public final int value;

	/** The Constant values. */
	private final static CCG001SystemType[] values = CCG001SystemType.values();

	/**
	 * Instantiates a new system type.
	 *
	 * @param value
	 *            the value
	 */
	private CCG001SystemType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the system type
	 */
	public static CCG001SystemType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CCG001SystemType val : CCG001SystemType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}