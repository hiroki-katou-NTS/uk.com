/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Enum PublicHolidayPeriod.
 */
// 公休管理期間
public enum PublicHolidayPeriod {
	
	/** The closure period. */
	// 締め期間
	CLOSURE_PERIOD(0, "Enum_ClosurePeriod"),

	/** The first day to last day. */
	// １日～末日
	FIRST_DAY_TO_LAST_DAY(1, "Enum_FirstDayToLastDay");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static PublicHolidayPeriod[] values = PublicHolidayPeriod.values();

	
	/**
	 * Instantiates a new public holiday period.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private PublicHolidayPeriod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the public holiday period
	 */
	public static PublicHolidayPeriod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PublicHolidayPeriod val : PublicHolidayPeriod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
