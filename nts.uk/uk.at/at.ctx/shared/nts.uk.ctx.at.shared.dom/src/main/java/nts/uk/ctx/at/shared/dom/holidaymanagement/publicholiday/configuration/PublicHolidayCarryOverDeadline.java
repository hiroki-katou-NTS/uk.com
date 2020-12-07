/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Enum PublicHolidayCarryOverDeadline.
 */
// 公休繰越期限
public enum PublicHolidayCarryOverDeadline {

//	/** The  1 month. */
//	//1ヶ月
//	_1_MONTH(0, "Enum_HolidayTransferLimit_OneMonth"),
//	
//	/** The  2 month. */
//	//2ヶ月
//	_2_MONTH(1, "Enum_HolidayTransferLimit_TwoMonth"),
//	
//	/** The  3 month. */
//	//3ヶ月
//	_3_MONTH(2, "Enum_HolidayTransferLimit_ThreeMonth"),
//	
//	/** The  4 month. */
//	//4ヶ月
//	_4_MONTH(3, "Enum_HolidayTransferLimit_FourMonth"),
//	
//	/** The  5 month. */
//	//5ヶ月
//	_5_MONTH(4, "Enum_HolidayTransferLimit_FiveMonth"),
//	
//	/** The  6 month. */
//	//6ヶ月
//	_6_MONTH(5, "Enum_HolidayTransferLimit_SixMonth"),
//	
//	/** The  7 month. */
//	//7ヶ月
//	_7_MONTH(6, "Enum_HolidayTransferLimit_SevenMonth"),
//	
//	/** The  8 month. */
//	//8ヶ月
//	_8_MONTH(7, "Enum_HolidayTransferLimit_EigntMonth"),
//	
//	/** The  9 month. */
//	//9ヶ月
//	_9_MONTH(8, "Enum_HolidayTransferLimit_NineMonth"),
//	
//	/** The  10 month. */
//	//10ヶ月
//	_10_MONTH(9, "Enum_HolidayTransferLimit_TenMonth"),
//	
//	/** The  11 month. */
//	//11ヶ月
//	_11_MONTH(10, "Enum_HolidayTransferLimit_ElevenMonth"),
//	
//	/** The  12 month. */
//	//12ヶ月
//	_12_MONTH(11, "Enum_HolidayTransferLimit_TwelveMonth"), 

	/** The current month. */
	// 当月
	CURRENT_MONTH(0, "Enum_HolidayTransferLimit_ThisMonth"),

	/** The indefinite. */
	// 無期限
	INDEFINITE(1, "Enum_HolidayTransferLimit_NoneLimit"),

	/** The year end. */
	// 年度末
	YEAR_END(2, "Enum_HolidayTransferLimit_YearEnd");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static PublicHolidayCarryOverDeadline[] values = PublicHolidayCarryOverDeadline.values();

	/**
	 * Instantiates a new public holiday carry over deadline.
	 *
	 * @param value  the value
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
