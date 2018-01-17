/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

/**
 * The Enum ResttimeAtr.
 */
public enum ResttimeAtr {

	/** The off day. */
	OFF_DAY(0, "流動勤務の休日出勤用勤務時間帯"),

	/** The half day. */
	HALF_DAY(1, "流動勤務の平日出勤用勤務時間帯");

	/** The value. */
	public final int value;
	
	/** The name. */
	public final String name;

	/**
	 * Instantiates a new resttime atr.
	 *
	 * @param type the type
	 * @param name the name
	 */
	ResttimeAtr(int type, String name) {
		this.value = type;
		this.name = name;
	}
}
