/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * The Enum TimeVacationDigestiveUnit.
 */
public enum TimeVacationDigestiveUnit {
	
	/** The One minute. */
	OneMinute(1),
	
	/** The Fifteen minute. */
	FifteenMinute(2),
	
	/** The Thirty minute. */
	ThirtyMinute(3),
	
	/** The One hour. */
	OneHour(4),
	
	/** The Two hour. */
	TwoHour(5);
	
	/** The value. */
	public int value;
	
	/**
	 * Instantiates a new time vacation digestive unit.
	 *
	 * @param value the value
	 */
	private TimeVacationDigestiveUnit(Integer value) {
		this.value = value;
	}
	
	public static TimeVacationDigestiveUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeVacationDigestiveUnit val : TimeVacationDigestiveUnit.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
