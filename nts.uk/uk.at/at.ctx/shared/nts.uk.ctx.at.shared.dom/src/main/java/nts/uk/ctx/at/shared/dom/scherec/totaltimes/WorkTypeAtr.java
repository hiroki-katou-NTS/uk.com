/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

/**
 * The Enum WorkTypeAtr.
 */
// 種類区分
public enum WorkTypeAtr {

	/** The worktype. */
	// 勤務種類区分
	WORKTYPE(0, "Enum_WorkTypeAtr_WorkType", "勤務種類区分"),

	/** The workingtime. */
	WORKINGTIME(1, "Enum_WorkTypeAtr_WorkingTime", "就業時間帯区分");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkTypeAtr[] values = WorkTypeAtr.values();

	/**
	 * Instantiates a new work type atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private WorkTypeAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the summary atr
	 */
	public static WorkTypeAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkTypeAtr val : WorkTypeAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
