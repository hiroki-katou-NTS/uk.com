/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

/**
 * The Enum UseClassification.
 */
// 使用区分
public enum UseClassification {
	// 使用する
	USE(0),
	// 使用しない
	DONOTUSE(1);

	/** The value. */
	public final int value;

	/**
	 * Instantiates a new use classification.
	 *
	 * @param type the type
	 */
	private UseClassification(int type) {
		this.value = type;
	}

}
