/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Enum PublicHolidayCarryOverDeadline.
 */
// 公休繰越期限
public enum PublicHolidayCarryOverDeadline {
	
	/** The  1 month. */
	//1ヶ月
	_1_MONTH(0, "Enum_1Month"),
	
	/** The  2 month. */
	//2ヶ月
	_2_MONTH(0, "Enum_2Month"),
	
	/** The  3 month. */
	//3ヶ月
	_3_MONTH(0, "Enum_3Month"),
	
	/** The  4 month. */
	//4ヶ月
	_4_MONTH(0, "Enum_4Month"),
	
	/** The  5 month. */
	//5ヶ月
	_5_MONTH(0, "Enum_5Month"),
	
	/** The  6 month. */
	//6ヶ月
	_6_MONTH(0, "Enum_6Month"),
	
	/** The  7 month. */
	//7ヶ月
	_7_MONTH(0, "Enum_7Month"),
	
	/** The  8 month. */
	//8ヶ月
	_8_MONTH(0, "Enum_8Month"),
	
	/** The  9 month. */
	//9ヶ月
	_9_MONTH(0, "Enum_9Month"),
	
	/** The  10 month. */
	//10ヶ月
	_10_MONTH(0, "Enum_10Month"),
	
	/** The  11 month. */
	//11ヶ月
	_11_MONTH(0, "Enum_11Month"),
	
	/** The  12 month. */
	//12ヶ月
	_12_MONTH(0, "Enum_12Month"),
	
	/** The year end. */
	// 年度末
	YEAR_END(0, "Enum_YearEnd"),
	
	/** The indefinite. */
	// 無期限
	INDEFINITE(0, "Enum_Indefinite"),
	
	/** The current month. */
	// 当月
	CURRENT_MONTH(0, "Enum_CurrentMonth");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static PublicHolidayCarryOverDeadline[] values = PublicHolidayCarryOverDeadline.values();

	
	/**
	 * Instantiates a new public holiday carry over deadline.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private PublicHolidayCarryOverDeadline(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the public holiday carry over deadline
	 */
	public static PublicHolidayCarryOverDeadline valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PublicHolidayCarryOverDeadline val : PublicHolidayCarryOverDeadline.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
