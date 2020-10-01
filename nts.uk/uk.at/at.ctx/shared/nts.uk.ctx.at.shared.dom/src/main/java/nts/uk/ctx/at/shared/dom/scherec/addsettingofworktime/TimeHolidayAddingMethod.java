/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum TimeHolidayAddingMethod.
 */
// 時間休暇加算方法
public enum TimeHolidayAddingMethod {
	// 相殺した時間のみ加算
	ADD_ONLY_FOR_OFFSET_TIME(0, "Enum_AddOnlyForOffsetTime"),

	// 使用した時間全て加算
	ADD_ALL_TIME_USED(1, "Enum_AddAllTimeUsed");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static TimeHolidayAddingMethod[] values = TimeHolidayAddingMethod.values();

	
	
	/**
	 * Instantiates a new time holiday adding method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private TimeHolidayAddingMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the time holiday adding method
	 */
	public static TimeHolidayAddingMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeHolidayAddingMethod val : TimeHolidayAddingMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
