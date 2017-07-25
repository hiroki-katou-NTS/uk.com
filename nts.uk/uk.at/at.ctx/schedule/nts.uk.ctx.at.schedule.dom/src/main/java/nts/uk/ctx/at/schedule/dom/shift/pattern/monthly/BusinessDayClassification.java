/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

/**
 * The Enum BusinessDayClassification.
 */
// 稼働日区分
public enum BusinessDayClassification {
	// 稼働日
	WORK_DAYS(0),
	// 法定内休日
	STATUTORY_HOLIDAYS(1),
	// 法定外休日
	NONE_STATUTORY_HOLIDAYS(2),
	// 祝日
	PUBLIC_HOLIDAYS(3);

	/** The value. */
	public final int value;

	/**
	 * Instantiates a new business day classification.
	 *
	 * @param type the type
	 */
	private BusinessDayClassification(int type) {
		this.value = type;
	}

}
