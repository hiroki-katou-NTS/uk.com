/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

/**
 * The Enum PersonalWorkAtr.
 */
public enum PersonalWorkAtr {
	
	/** The saturday. */
	// 土曜日
	SATURDAY(0, "Enum_PersonalWorkAtr_saturday", "土曜日"),

	/** The sunday. */
	// 日曜日
	SUNDAY(1, "Enum_PersonalWorkAtr_sunday", "日曜日"),

	/** The monday. */
	// 月曜日
	MONDAY(2, "Enum_PersonalWorkAtr_monday", "月曜日"),

	/** The thursday. */
	// 木曜日
	THURSDAY(3, "Enum_PersonalWorkAtr_thursday", "木曜日"),

	/** The wednesday. */
	// 水曜日
	WEDNESDAY(4, "Enum_PersonalWorkAtr_wednesday", "水曜日"),

	/** The tuesday. */
	// 火曜日
	TUESDAY(5, "Enum_PersonalWorkAtr_tuesday", "火曜日"),

	/** The friday. */
	// 金曜日
	FRIDAY(6, "Enum_PersonalWorkAtr_friday", "火曜日"),

	/** The holiday work. */
	// 休日出勤時
	HOLIDAY_WORK(7, "Enum_PersonalWorkAtr_holidayWork", "休日出勤時"),

	/** The holiday time. */
	// 休日時
	HOLIDAY_TIME(8, "Enum_PersonalWorkAtr_holidayTime", "休日時"),

	/** The weekday time. */
	// 平日時
	WEEKDAY_TIME(9, "Enum_PersonalWorkAtr_weekdayTime", "平日時"),

	/** The public holiday work. */
	// 公休出勤時
	PUBLIC_HOLIDAY_WORK(10, "Enum_PersonalWorkAtr_publicHolidayWork", "公休出勤時"),

	/** The inlaw break time. */
	// 法内休出時
	INLAW_BREAK_TIME(11, "Enum_PersonalWorkAtr_inLawBreakTime", "法内休出時"),

	/** The outside law break time. */
	// 法外休出時
	OUTSIDE_LAW_BREAK_TIME(12, "Enum_PersonalWorkAtr_outsideLawBreakTime", "法外休出時"),
	
	/** The holiday attendance time. */
	// 祝日出勤時
	HOLIDAY_ATTENDANCE_TIME(13, "Enum_PersonalWorkAtr_holidayAttendanceTime", "祝日出勤時");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static PersonalWorkAtr[] values = PersonalWorkAtr.values();

	/**
	 * Instantiates a new personal work atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private PersonalWorkAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the personal work atr
	 */
	public static PersonalWorkAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PersonalWorkAtr val : PersonalWorkAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}

}
