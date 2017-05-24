/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Enum YearVacationTimeUnit.
 */
public enum YearVacationTimeUnit {

	/** The One minute. */
	OneMinute(0, "1分"),

	/** The Fifteen minute. */
	FifteenMinute(1, "15分"),

	/** The Thirty minute. */
	ThirtyMinute(2, "30分"),

	/** The One hour. */
	OneHour(3, "1時間"),

	/** The Two hour. */
	TwoHour(4, "2時間");

	/** The value. */
	public int value;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static YearVacationTimeUnit[] values = YearVacationTimeUnit.values();

	/**
	 * Instantiates a new year vacation time unit.
	 *
	 * @param value the value
	 */
	private YearVacationTimeUnit(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the year vacation time unit
	 */
	public static YearVacationTimeUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (YearVacationTimeUnit val : YearVacationTimeUnit.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
