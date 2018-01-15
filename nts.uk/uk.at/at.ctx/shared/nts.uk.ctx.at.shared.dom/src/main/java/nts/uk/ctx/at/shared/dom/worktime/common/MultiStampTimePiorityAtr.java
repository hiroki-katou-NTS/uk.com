/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum MultiStampTimePiorityAtr.
 */
//複数打刻時優先区分
public enum MultiStampTimePiorityAtr {
	
	/** The before piority. */
	// 前優先
	BEFORE_PIORITY(0, "Enum_MultiStampTimePiorityAtr_beforePiority", "前優先"),

	/** The after piority. */
	// 後優先 
	AFTER_PIORITY(1, "Enum_MultiStampTimePiorityAtr_afterPiority", "後優先");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static MultiStampTimePiorityAtr[] values = MultiStampTimePiorityAtr.values();

	/**
	 * Instantiates a new multi stamp time piority atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private MultiStampTimePiorityAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the multi stamp time piority atr
	 */
	public static MultiStampTimePiorityAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MultiStampTimePiorityAtr val : MultiStampTimePiorityAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
