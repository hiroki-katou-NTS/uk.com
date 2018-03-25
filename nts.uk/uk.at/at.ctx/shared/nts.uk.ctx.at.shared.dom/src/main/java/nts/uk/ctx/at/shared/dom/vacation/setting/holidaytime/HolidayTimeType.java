/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.holidaytime;

/**
 * 時間年休より優先する休暇
 */
public enum HolidayTimeType {

	/**時間代休**/
	TimeOffperiod(0, "時間代休", "時間代休"),
	
	/**60H超過時間**/
	SixtyOvertime(1, "60H超過時間", "60H超過時間");
	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	//private final static HolidayTimeType[] values = HolidayTimeType.values();

	/**
	 * Instantiates a new vacation type.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private HolidayTimeType(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the holidate time type
	 */
	public static HolidayTimeType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (HolidayTimeType val : HolidayTimeType.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
