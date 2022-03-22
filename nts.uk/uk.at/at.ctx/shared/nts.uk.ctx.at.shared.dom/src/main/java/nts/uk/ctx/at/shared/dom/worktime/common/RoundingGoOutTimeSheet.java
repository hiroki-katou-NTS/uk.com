/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum RoundingGoOutTimeSheet.
 */
//外出時間帯の丸め方法
public enum RoundingGoOutTimeSheet {

	/** The total and rounding. */
	// 各時間帯の逆丸め
	REVERSE_ROUNDING_EACH_TIMEZONE(0, "Enum_RoundingGoOutTimeSheet_reverseRoundingEachTimezone", "各時間帯の逆丸め"),

	/** The rounding and total. */
	// 個別の丸め
	INDIVIDUAL_ROUNDING(1, "Enum_RoundingGoOutTimeSheet_individualRounding", "個別の丸め");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RoundingGoOutTimeSheet[] values = RoundingGoOutTimeSheet.values();

	/**
	 * Instantiates a new go out time rounding method.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private RoundingGoOutTimeSheet(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the go out time rounding method
	 */
	public static RoundingGoOutTimeSheet valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingGoOutTimeSheet val : RoundingGoOutTimeSheet.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

	/**
	 * 個別の丸めか
	 */
	public boolean isIndividualRounding() {
		return INDIVIDUAL_ROUNDING.equals(this);
	}
}
