/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum WorkSystemAtr.
 */
public enum WorkSystemAtr {

	/** The day shift. */
	// 日勤
	DAY_SHIFT(0, "Enum_WorkSystemAtr_dayShift", "日勤"),

	/** The night shift. */
	// 夜勤
	NIGHT_SHIFT(1, "Enum_WorkSystemAtr_nightShift", "夜勤");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkSystemAtr[] values = WorkSystemAtr.values();

	/**
	 * Instantiates a new work system atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkSystemAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the work system atr
	 */
	public static WorkSystemAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkSystemAtr val : WorkSystemAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
