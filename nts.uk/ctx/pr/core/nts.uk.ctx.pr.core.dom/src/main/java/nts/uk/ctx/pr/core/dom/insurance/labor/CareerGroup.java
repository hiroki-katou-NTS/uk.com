/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.insurance.labor;

/**
 * The Enum CareerGroup.
 */
public enum CareerGroup {

	/** The Agroforestry. */
	Agroforestry(0, "Agroforestry"),

	/** The Contruction. */
	Contruction(1, "Contruction"),

	/** The Other. */
	Other(2, "Other");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static CareerGroup[] values = CareerGroup.values();

	/**
	 * Instantiates a new career group.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private CareerGroup(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the career group
	 */
	public static CareerGroup valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CareerGroup val : CareerGroup.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
