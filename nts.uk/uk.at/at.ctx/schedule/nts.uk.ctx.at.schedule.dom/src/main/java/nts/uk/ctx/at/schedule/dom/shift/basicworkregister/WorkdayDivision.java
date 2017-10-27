/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

/**
 * The Enum 稼働日区分.
 */
public enum WorkdayDivision {

	/** The workingdays. */
	WORKINGDAYS(0, "稼働日", "稼働日"),

	/** The non workingday inlaw. */
	NON_WORKINGDAY_INLAW(1, "非稼働日（法内）", "非稼働日（法内）"),

	/** The non workingday extralegal. */
	NON_WORKINGDAY_EXTRALEGAL(2, "非稼働日（法外）", "非稼働日（法外）");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	private static final WorkdayDivision[] values = WorkdayDivision.values();

	/**
	 * Instantiates a new work day division.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkdayDivision(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Values of.
	 *
	 * @param value the value
	 * @return the work day division
	 */
	public static WorkdayDivision valuesOf(int value) {
		// Find value
		for (WorkdayDivision val : WorkdayDivision.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Find Not Found.
		return null;
	}
}
