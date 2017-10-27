/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

/**
 * The Enum EstimateTargetClassification.
 */
// 目安対象区分
public enum EstimateTargetClassification {
	
	/** The yearly. */
	// 年間
	YEARLY(0, "年間"),

	/** The january. */
	// 1月度
	JANUARY(1, "1月度"),

	/** The february. */
	// 2月度
	FEBRUARY(2, "2月度"),
	
	/** The march. */
	// 3月度
	MARCH(3, "3月度"),
	
	/** The april. */
	// 4月度
	APRIL(4, "4月度"),
	
	/** The may. */
	// 5月度
	MAY(5, "5月度"),
	
	/** The june. */
	// 6月度
	JUNE(6, "6月度"),
	
	/** The july. */
	// 7月度
	JULY(7, "7月度"),
	
	/** The august. */
	// 8月度
	AUGUST(8, "8月度"),
	
	/** The september. */
	// 9月度
	SEPTEMBER(9, "9月度"),
	
	/** The october. */
	// 10月度
	OCTOBER(10, "10月度"),
	
	/** The november. */
	// 11月度
	NOVEMBER(11, "11月度"),
	
	/** The december. */
	// 12月度
	DECEMBER(12, "12月度");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static EstimateTargetClassification[] values = EstimateTargetClassification.values();

	/**
	 * Instantiates a new estimate target classification.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private EstimateTargetClassification(int value,String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the estimate term of use
	 */
	public static EstimateTargetClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EstimateTargetClassification val : EstimateTargetClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
