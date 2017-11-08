/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

/**
 * The Enum Rounding.
 */
// 端数処理
public enum NumberRounding {

	/** The truncation. */
	// 切り捨て
	TRUNCATION(0, "Enum_Rounding_Truncation"),

	/** The round up. */
	// 切り上げ
	ROUND_UP(1, "Enum_Rounding_Round_Up"),

	/** The down 4 up 5. */
	// 四捨五入
	DOWN_4_UP_5(5, "Enum_Rounding_Down_4_Up_5");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static NumberRounding[] values = NumberRounding.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private NumberRounding(int value, String nameId) {
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
	public static NumberRounding valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (NumberRounding val : NumberRounding.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
