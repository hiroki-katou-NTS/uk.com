/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

/**
 * The Enum StartingMonthType.
 */
// 起算月
public enum StartingMonthType {

	/*
	 * ３６協定起算月
	 */
	/** The january. */
	// 0: 1月
	JANUARY(0,"January", "0:00"),

	/** The february. */
	// 1: 2月
	FEBRUARY(1,"February","0:00"),

	/** The march. */
	// 2: 3月
	MARCH(2,"March","0:00"),

	/** The april. */
	// 3: 4月
	APRIL(3,"April","160:00"),

	/** The may. */
	// 4: 5月
	MAY(4,"May","184:00"),

	/** The june. */
	// 5: 6月
	JUNE(5,"June","176:00"),

	/** The july. */
	// 6: 7月
	JULY(6,"July","168:00"),

	/** The august. */
	// 7: 8月
	AUGUST(7,"August","0:00"),

	/** The september. */
	// 8: 9月
	SEPTEMBER(8,"September", "0:00"),

	/** The october. */
	// 9: 10月
	OCTOBER(9,"October","0:00"),

	/** The november. */
	// 10: 11月
	NOVEMBER(10,"November","0:00"),

	/** The december. */
	// 11: 12月
	DECEMBER(11,"December","0:00");

	/** The value. */
	public final int value;
	public String nameId;
	public String hour;

	/**
	 * Instantiates a new starting month type.
	 *
	 * @param type
	 *            the type
	 */
	private StartingMonthType(int type, String nameId, String hour) {
		this.value = type;
		this.nameId = nameId;
		this.hour = hour;	
	}

}
