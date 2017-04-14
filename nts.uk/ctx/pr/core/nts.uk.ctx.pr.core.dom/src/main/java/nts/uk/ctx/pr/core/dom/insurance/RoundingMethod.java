/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.insurance;

/**
 * The Enum RoundingMethod.
 */
public enum RoundingMethod {

	/** The Round up. */
	RoundUp(0, "RoundUp"),

	/** The Truncation. */
	Truncation(1, "Truncation"),

	/** The Round down. */
	RoundDown(2, "RoundDown"),

	/** The Down 5 up 6. */
	Down5_Up6(3, "Down5_Up6"),

	/** The Down 4 up 5. */
	Down4_Up5(4, "Down4_Up5");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static RoundingMethod[] values = RoundingMethod.values();

	/**
	 * Instantiates a new rounding method.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private RoundingMethod(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding method
	 */
	public static RoundingMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingMethod val : RoundingMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
