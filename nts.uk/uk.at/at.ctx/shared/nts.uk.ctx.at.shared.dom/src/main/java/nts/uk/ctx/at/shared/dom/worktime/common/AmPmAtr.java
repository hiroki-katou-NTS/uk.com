/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;


/**
 * The Enum AmPmAtr.
 */
// 午前午後区分
public enum AmPmAtr {

	/** The one day. */
	// １日
	ONE_DAY(0, "Enum_AmPmAtr_OneDay", "１日"),

	/** The am. */
	// 午前
	AM(1, "Enum_AmPmAtr_Am", "午前"),

	/** The pm. */
	// 午後
	PM(2, "Enum_AmPmAtr_Pm", "午後");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AmPmAtr[] values = AmPmAtr.values();

	/**
	 * Instantiates a new am pm atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private AmPmAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the am pm atr
	 */
	public static AmPmAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AmPmAtr val : AmPmAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
