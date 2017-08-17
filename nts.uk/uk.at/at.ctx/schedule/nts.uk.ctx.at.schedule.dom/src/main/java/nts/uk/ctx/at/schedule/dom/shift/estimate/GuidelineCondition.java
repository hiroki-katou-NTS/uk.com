/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

/**
 * The Enum EstimateTermOfUse.
 */
// 目安利用条件
public enum GuidelineCondition {

	/** The condition 1st. */
	// 条件1
	CONDITION_1ST(1, "Enum_AmPmClassification_Condition_1st", "条件1"),

	/** The condition 2nd. */
	// 条件2
	CONDITION_2ND(2, "Enum_AmPmClassification_Condition_2nd", "条件2"),

	/** The condition 3rd. */
	// 条件3
	CONDITION_3RD(3, "Enum_AmPmClassification_Condition_3rd", "条件3"),

	/** The condition 4th. */
	// 条件4
	CONDITION_4TH(4, "Enum_AmPmClassification_Condition_4th", "条件4"),

	/** The condition 5th. */
	// 条件5
	CONDITION_5TH(5, "Enum_AmPmClassification_Condition_5th", "条件5");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static GuidelineCondition[] values = GuidelineCondition.values();

	/**
	 * Instantiates a new estimate term of use.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private GuidelineCondition(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the estimate term of use
	 */
	public static GuidelineCondition valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GuidelineCondition val : GuidelineCondition.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
