/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum Rounding.
 */
public enum Rounding {

	/** The rounding down. */
	// 切り捨て
	ROUNDING_DOWN(0, "Enum_Rounding_Down"),

	/** The rounding up. */
	// 切り上げ
	ROUNDING_UP(1, "Enum_Rounding_Up"),

	/** The rounding lessthanorequal. */
	// 未満切捨、以上切上
	ROUNDING_LESSTHANOREQUAL(2, "Enum_Rounding_LessThanOrEqual");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static Rounding[] values = Rounding.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private Rounding(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static Rounding valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Rounding val : Rounding.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
