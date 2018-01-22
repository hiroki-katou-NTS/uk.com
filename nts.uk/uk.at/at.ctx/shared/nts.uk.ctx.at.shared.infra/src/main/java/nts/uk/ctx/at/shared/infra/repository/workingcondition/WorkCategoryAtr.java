/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

/**
 * The Enum WorkCategoryAtr.
 */
public enum WorkCategoryAtr {
	
	/** The weekday time. */
	// 平日時
	WEEKDAY_TIME(0, "Enum_WorkCategoryAtr_weekdayTime", "平日時"),
	
	/** The holiday work. */
	// 休日出勤時
	HOLIDAY_WORK(1, "Enum_WorkCategoryAtr_holidayWork", "休日出勤時"),
	
	/** The inlaw break time. */
	// 法内休出時
	INLAW_BREAK_TIME(2, "Enum_WorkCategoryAtr_inLawBreakTime", "法内休出時"),
	
	/** The outside law break time. */
	// 法外休出時
	OUTSIDE_LAW_BREAK_TIME(3, "Enum_WorkCategoryAtr_outsideLawBreakTime", "法外休出時"),
	
	/** The holiday attendance time. */
	// 祝日出勤時
	HOLIDAY_ATTENDANCE_TIME(4, "Enum_WorkCategoryAtr_holidayAttendanceTime", "祝日出勤時"),

	/** The public holiday work. */
	// 公休出勤時
	PUBLIC_HOLIDAY_WORK(5, "Enum_WorkCategoryAtr_publicHolidayWork", "公休出勤時"),

	/** The holiday time. */
	// 休日時
	HOLIDAY_TIME(6, "Enum_WorkCategoryAtr_holidayTime", "休日時");


	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkCategoryAtr[] values = WorkCategoryAtr.values();

	/**
	 * Instantiates a new work category atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkCategoryAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the work category atr
	 */
	public static WorkCategoryAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkCategoryAtr val : WorkCategoryAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}

}
