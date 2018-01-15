/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

/**
 * The Enum DayOfWeekAtr.
 */
public enum DayOfWeekAtr {

	/** The monday. */
	// 月曜日
	MONDAY(0, "Enum_DayOfWeekAtr_monday", "月曜日"),
	
	/** The tuesday. */
	// 火曜日
	TUESDAY(1, "Enum_DayOfWeekAtr_tuesday", "火曜日"),
	
	/** The wednesday. */
	// 水曜日
	WEDNESDAY(2, "Enum_DayOfWeekAtr_wednesday", "水曜日"),
		
	/** The thursday. */
	// 木曜日
	THURSDAY(3, "Enum_DayOfWeekAtr_thursday", "木曜日"),
	
	/** The friday. */
	// 金曜日
	FRIDAY(4, "Enum_DayOfWeekAtr_friday", "火曜日"),
	
	/** The saturday. */
	// 土曜日
	SATURDAY(5, "Enum_DayOfWeekAtr_saturday", "土曜日"),

	/** The sunday. */
	// 日曜日
	SUNDAY(6, "Enum_DayOfWeekAtr_sunday", "日曜日");


	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static DayOfWeekAtr[] values = DayOfWeekAtr.values();

	/**
	 * Instantiates a new personal work atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private DayOfWeekAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the day of week atr
	 */
	public static DayOfWeekAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DayOfWeekAtr val : DayOfWeekAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
