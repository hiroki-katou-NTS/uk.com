/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

/**
 * The Enum EstComparisonAtr.
 */
// 目安比較対象区分
public enum EstComparisonAtr {

	/** The pre determined. */
	// 所定時間
	PRE_DETERMINED(1, "所定時間"),
	
	/** The total working hours. */
	// 総労働時間
	TOTAL_WORKING_HOURS(2, "総労働時間"),
	
	/** The per cost time. */
	// 人件費時間
	PER_COST_TIME(3, "人件費時間");
	
	/** The value. */
	public int value;
	
	/** The description. */
	public String description;
	
	/** The Constant values. */
	private static final EstComparisonAtr[] values =  EstComparisonAtr.values();
	
	/**
	 * Instantiates a new est comparison atr.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private EstComparisonAtr (int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the est comparison atr
	 */
	public static EstComparisonAtr valueOf (Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		// Find
		for (EstComparisonAtr val: EstComparisonAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not Found
		return null;
	}
	
}
