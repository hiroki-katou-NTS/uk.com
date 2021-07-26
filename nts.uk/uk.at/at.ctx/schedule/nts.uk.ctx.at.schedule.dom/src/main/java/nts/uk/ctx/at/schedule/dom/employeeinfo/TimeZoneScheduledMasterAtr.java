/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

/**
 * The Class TimeZoneScheduledMasterAtr.
 */
// 勤務予定の時間帯マスタ参照区分
public enum TimeZoneScheduledMasterAtr {
	
	/** The follow master reference. */
	// マスタ参照区分に従う
	FOLLOW_MASTER_REFERENCE(0, "Enum_TimeZoneScheduledMasterAtr_FollowMasterReference", "マスタ参照区分に従う"),
	
	/** The personal day of week. */
	// 個人曜日別
	PERSONAL_DAY_OF_WEEK(2, "Enum_TimeZoneScheduledMasterAtr_PersonalDayOfWeek", "個人曜日別"),

	/** The personal work daily. */
	//平日時  (個人勤務日別 old)
	WEEKDAYS(1, "Enum_TimeZoneScheduledMasterAtr_PersonalWorkDaily", "個人勤務日別");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static TimeZoneScheduledMasterAtr[] values = TimeZoneScheduledMasterAtr.values();

	/**
	 * Instantiates a new time zone scheduled master atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private TimeZoneScheduledMasterAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the time zone scheduled master atr
	 */
	public static TimeZoneScheduledMasterAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeZoneScheduledMasterAtr val : TimeZoneScheduledMasterAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
