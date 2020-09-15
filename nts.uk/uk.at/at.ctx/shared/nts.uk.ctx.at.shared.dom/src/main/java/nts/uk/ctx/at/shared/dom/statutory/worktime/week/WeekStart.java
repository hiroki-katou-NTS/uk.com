/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.week;

/**
 * The Enum WeekStart.
 */
//週開始
public enum WeekStart {

	/** The Monday. */
	//月曜日
	Monday(0),

	/** The Tuesday. */
	//火曜日
	Tuesday(1),

	/** The Wednesday. */
	//水曜日
	Wednesday(2),

	/** The Thursday. */
	//木曜日
	Thursday(3),

	/** The Friday. */
	//金曜日
	Friday(4),

	/** The Saturday. */
	//土曜日
	Saturday(5),

	/** The Sunday. */
	//日曜日
	Sunday(6),

	/** The Tightening start date. */
	//締め開始日
	TighteningStartDate(7);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static WeekStart[] values = WeekStart.values();

	/**
	 * Instantiates a new week start.
	 *
	 * @param value the value
	 */
	private WeekStart(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the week start
	 */
	public static WeekStart valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WeekStart val : WeekStart.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
