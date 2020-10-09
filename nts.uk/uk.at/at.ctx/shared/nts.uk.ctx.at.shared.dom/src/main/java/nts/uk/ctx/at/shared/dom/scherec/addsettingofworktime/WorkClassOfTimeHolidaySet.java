/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum WorkClassOfTimeHolidaySet.
 */
// 時間休暇加算設定用勤務区分
public enum WorkClassOfTimeHolidaySet {
	// 流動勤務
	WORK_FOR_FLOW(0, "Enum_WorkForFlow"),

	// フレックス勤務
	WORK_FOR_FLEX(1, "Enum_WorkForFlex");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static WorkClassOfTimeHolidaySet[] values = WorkClassOfTimeHolidaySet.values();

	
	/**
	 * Instantiates a new work class of time holiday set.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private WorkClassOfTimeHolidaySet(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the work class of time holiday set
	 */
	public static WorkClassOfTimeHolidaySet valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkClassOfTimeHolidaySet val : WorkClassOfTimeHolidaySet.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
