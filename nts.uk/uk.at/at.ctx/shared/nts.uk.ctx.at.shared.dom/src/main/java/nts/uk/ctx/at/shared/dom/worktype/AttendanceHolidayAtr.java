/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

/**
 * The Enum AttendanceHolidayAtr.
 */
// 出勤休日区分
public enum AttendanceHolidayAtr {
	
	/** The full time. */
	// 1日出勤系
	FULL_TIME(0, "1日出勤系" ,"Enum_AttendanceHolidayAtr_Fulltime" ),
	
	/** The holiday. */
	// 1日休日系
	HOLIDAY(1, "１日休日系" ,"Enum_AttendanceHolidayAtr_Holiday" ),
	
	/** The morning. */
	// 午前出勤系
	MORNING(2, "午前出勤系" ,"Enum_AttendanceHolidayAtr_Morning" ),
	
	/** The afternoon. */
	// 午後出勤系
	AFTERNOON(3, "午後出勤系" ,"Enum_AttendanceHolidayAtr_Afternoon" );
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static AttendanceHolidayAtr[] values = AttendanceHolidayAtr.values();


	/**
	 * Instantiates a new attendance holiday atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private AttendanceHolidayAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the attendance holiday atr
	 */
	public static AttendanceHolidayAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AttendanceHolidayAtr val : AttendanceHolidayAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
