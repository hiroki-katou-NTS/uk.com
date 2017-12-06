/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum GoLeavingWorkAtr.
 */
public enum GoLeavingWorkAtr {

	/** The go work. */
	// 出勤
	GO_WORK(0, "Enum_GoLeavingWorkAtr_goWork", "出勤"),

	/** The leaving work. */
	// 退勤
	LEAVING_WORK(1, "Enum_GoLeavingWorkAtr_leavingWork", "退勤");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static GoLeavingWorkAtr[] values = GoLeavingWorkAtr.values();

	/**
	 * Instantiates a new go leaving work atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private GoLeavingWorkAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the go leaving work atr
	 */
	public static GoLeavingWorkAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GoLeavingWorkAtr val : GoLeavingWorkAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
