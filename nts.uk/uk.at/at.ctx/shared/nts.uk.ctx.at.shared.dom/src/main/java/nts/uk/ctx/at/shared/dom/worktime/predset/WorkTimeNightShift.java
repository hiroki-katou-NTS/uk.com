/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

/**
 * The Enum WorkTimeNightShift.
 */
public enum WorkTimeNightShift {

	/** The previous day. */
	// 日勤
	DAY_SHIFT(0, "Enum_WorkTimeNightShift_Day_Shift", "日勤"),

	/** The today. */
	// 夜勤
	NIGHT_SHIFT(1, "Enum_WorkTimeNightShift_Night_Shift", "夜勤");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkTimeNightShift[] values = WorkTimeNightShift.values();

	/**
	 * Instantiates a new work time night shift.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private WorkTimeNightShift(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the work time night shift
	 */
	public static WorkTimeNightShift valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkTimeNightShift val : WorkTimeNightShift.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
