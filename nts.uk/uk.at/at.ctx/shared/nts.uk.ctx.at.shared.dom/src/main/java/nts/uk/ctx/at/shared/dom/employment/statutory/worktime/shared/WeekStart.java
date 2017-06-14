/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

/**
 * The Enum WeekStart.
 */
public enum WeekStart {

	/** The Monday. */
	Monday(0),

	/** The Tuesday. */
	Tuesday(1),

	/** The Wednesday. */
	Wednesday(2),

	/** The Thursday. */
	Thursday(3),

	/** The Friday. */
	Friday(4),

	/** The Saturday. */
	Saturday(5),

	/** The Sunday. */
	Sunday(6),

	/** The Tightening start date. */
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
