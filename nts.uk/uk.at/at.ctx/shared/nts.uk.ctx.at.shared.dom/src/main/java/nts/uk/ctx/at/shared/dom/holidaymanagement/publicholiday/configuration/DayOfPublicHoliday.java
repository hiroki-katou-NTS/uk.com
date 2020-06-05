/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Enum DayOfPublicHoliday.
 */
// 公休起算日指定
public enum DayOfPublicHoliday {
	
	/** The designate by year month day. */
	/**
	 *  年月日で指定する
	 */
	DESIGNATE_BY_YEAR_MONTH_DAY(0, "Enum_DesignateByYearMonthDay"),

	/** The designate by month day. */
	/** 月日で指定する */
	DESIGNATE_BY_MONTH_DAY(1, "Enum_DesignateByMonthDay");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static DayOfPublicHoliday[] values = DayOfPublicHoliday.values();

	
	/**
	 * Instantiates a new day of public holiday.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private DayOfPublicHoliday(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the day of public holiday
	 */
	public static DayOfPublicHoliday valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DayOfPublicHoliday val : DayOfPublicHoliday.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
