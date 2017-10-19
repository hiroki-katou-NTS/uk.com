/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

/**
 * The Enum ConfirmedAtr.
 */
// 予定確定区分
public enum ConfirmedAtr {

	/** The unsettled. */
	// 未確定
	UNSETTLED(0, "Enum_ConfirmedAtr_unsettled", " 未確定"),
	// 確定済み
	CONFIRMED(1, "Enum_ConfirmedAtr_confirmed", "確定済み");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ConfirmedAtr[] values = ConfirmedAtr.values();

	/**
	 * Instantiates a new re create atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private ConfirmedAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the re create atr
	 */
	public static ConfirmedAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ConfirmedAtr val : ConfirmedAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
