/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

/**
 * The Enum ChildCareScheduleRound.
 */
// 予定育児介護回数
public enum ChildCareScheduleRound {
	
	/** The one. */
	ONE(1, "Enum_ChildCareScheduleRound_One"),

	/** The two. */
	TWO(2, "Enum_ChildCareScheduleRound_Two");
	
	/** The value. */
	public final int value;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ChildCareScheduleRound[] values = ChildCareScheduleRound.values();

	/**
	 * Instantiates a new execution status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ChildCareScheduleRound(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the child care schedule round
	 */
	public static ChildCareScheduleRound valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ChildCareScheduleRound val : ChildCareScheduleRound.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
