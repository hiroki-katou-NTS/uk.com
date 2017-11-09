/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime_old;

/**
 * The Enum AmPmClassification.
 */
// 午前午後区分
public enum AmPmClassification {

	/** The one day. */
	// １日
	ONE_DAY(0, "Enum_AmPmClassification_OneDay", "１日"),

	/** The am. */
	// 午前
	AM(1, "Enum_AmPmClassification_Am", "午前"),

	/** The pm. */
	// 午後
	PM(2, "Enum_AmPmClassification_Pm", "午後");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AmPmClassification[] values = AmPmClassification.values();

	/**
	 * Instantiates a new am pm classification.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private AmPmClassification(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the am pm classification
	 */
	public static AmPmClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AmPmClassification val : AmPmClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
