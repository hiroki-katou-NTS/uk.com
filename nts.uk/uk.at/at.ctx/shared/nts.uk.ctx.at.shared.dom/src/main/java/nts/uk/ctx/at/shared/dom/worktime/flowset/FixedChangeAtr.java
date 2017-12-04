/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum FixedChangeAtr.
 */
//所定変動区分
public enum FixedChangeAtr {

	/** The not change. */
	// 変動しない
	NOT_CHANGE(0, "Enum_FixedChangeAtr_notChange", "変動しない"),

	/** The after shift. */
	// 後にズラす
	AFTER_SHIFT(1, "Enum_FixedChangeAtr_afterShift", "後にズラす"),

	/** The before after shift. */
	// 前にも後にもズラす
	BEFORE_AFTER_SHIFT(2, "Enum_FixedChangeAtr_beforeAfterShift", "前にも後にもズラす");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FixedChangeAtr[] values = FixedChangeAtr.values();

	/**
	 * Instantiates a new fixed change atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private FixedChangeAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the fixed change atr
	 */
	public static FixedChangeAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FixedChangeAtr val : FixedChangeAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
