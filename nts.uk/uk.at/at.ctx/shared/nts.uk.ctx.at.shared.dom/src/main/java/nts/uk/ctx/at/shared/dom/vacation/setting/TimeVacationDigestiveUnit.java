/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * The Enum YearVacationTimeUnit.
 */
public enum TimeVacationDigestiveUnit {

	/** The One minute. */
	OneMinute(0, "1分","1分"),

	/** The Fifteen minute. */
	FifteenMinute(1, "15分","15分"),

	/** The Thirty minute. */
	ThirtyMinute(2, "30分","30分"),

	/** The One hour. */
	OneHour(3, "1時間","1時間"),

	/** The Two hour. */
	TwoHour(4, "2時間","2時間");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TimeVacationDigestiveUnit[] values = TimeVacationDigestiveUnit.values();

	/**
	 * Instantiates a new manage distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private TimeVacationDigestiveUnit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the manage distinct
	 */
	public static TimeVacationDigestiveUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeVacationDigestiveUnit val : TimeVacationDigestiveUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
