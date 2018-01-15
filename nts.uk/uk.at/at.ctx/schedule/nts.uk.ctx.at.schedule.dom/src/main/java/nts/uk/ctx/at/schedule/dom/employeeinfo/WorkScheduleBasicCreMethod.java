/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

/**
 * The Enum WorkScheduleBasicCreMethod.
 */
// 勤務予定基本作成方法
public enum WorkScheduleBasicCreMethod {

	/** The business day calendar. */
	// 営業日カレンダー
	BUSINESS_DAY_CALENDAR(0, "Enum_WorkScheduleBasicCreMethod_BusinessDayCalendar", "営業日カレンダー"),

	/** The classification. */
	// 月間パターン
	MONTHLY_PATTERN(1, "Enum_WorkScheduleBasicCreMethod_MonthlyPattern", "月間パターン"),
	
	/** The personal day of week. */
	// 個人曜日別
	PERSONAL_DAY_OF_WEEK(2, "Enum_WorkScheduleBasicCreMethod_PersonalDayOfWeek", "個人曜日別");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkScheduleBasicCreMethod[] values = WorkScheduleBasicCreMethod.values();

	/**
	 * Instantiates a new time zone scheduled master atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkScheduleBasicCreMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the work schedule basic create method
	 */
	public static WorkScheduleBasicCreMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkScheduleBasicCreMethod val : WorkScheduleBasicCreMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
