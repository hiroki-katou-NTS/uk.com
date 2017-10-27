/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

/**
 * The Enum DayOfWeek.
 */
public enum DayOfWeek {
	// 月曜日
	MONDAY(2),
	// 火曜日
	TUESDAY(3),
	// 水曜日
	WEDNESDAY(4),
	// 木曜日
	THURSDAY(5),
	// 金曜日
	FRIDAY(6),
	// 土曜日
	SATURDAY(7),
	// 日曜日
	SUNDAY(8);

	/** The value. */
	public final int value;

	/**
	 * Instantiates a new day of week.
	 *
	 * @param type the type
	 */
	private DayOfWeek(int type) {
		this.value = type;
	}

}
