/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

/**
 * The Enum TimeDayAtr.
 */
public enum TimeDayAtr {

	/** The previous day. */
	// 前日
	PREVIOUS_DAY(0, "Enum_DayAtr_PreviousDay", "前日"),

	/** The today. */
	// 当日
	TODAY(1, "Enum_DayAtr_Day", "当日"),

	/** The next day. */
	// 前日
	NEXT_DAY(2, "Enum_DayAtr_NextDay", "翌日"),

	/** The skip day. */
	// 翌々日
	SKIP_DAY(3, "Enum_DayAtr_SkipDay", "翌々日");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static TimeDayAtr[] values = TimeDayAtr.values();

	/**
	 * Instantiates a new time day atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private TimeDayAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the time day atr
	 */
	public static TimeDayAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeDayAtr val : TimeDayAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
