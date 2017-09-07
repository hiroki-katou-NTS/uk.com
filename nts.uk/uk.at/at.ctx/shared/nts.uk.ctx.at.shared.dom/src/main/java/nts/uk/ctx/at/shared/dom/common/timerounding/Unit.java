/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.timerounding;

/**
 * The Enum Unit.
 */
// 単位
public enum Unit {

	/** The rounding time 1min. */
	// 時系列（原則）
	ROUNDING_TIME_1MIN(1, "Enum_RoundingTime_1Min", "1分"),
	
	/** The rounding time 5min. */
	ROUNDING_TIME_5MIN(1, "Enum_RoundingTime_5Min", "5分"),
	
	/** The rounding time 6min. */
	ROUNDING_TIME_6MIN(1, "Enum_RoundingTime_6Min", "6分"),
	
	/** The rounding time 10min. */
	ROUNDING_TIME_10MIN(1, "Enum_RoundingTime_10Min", "10分"),
	
	/** The rounding time 15min. */
	ROUNDING_TIME_15MIN(1, "Enum_RoundingTime_15Min", "15分"),
	
	/** The rounding time 20min. */
	ROUNDING_TIME_20MIN(1, "Enum_RoundingTime_20Min", "20分"),
	
	/** The rounding time 30min. */
	ROUNDING_TIME_30MIN(1, "Enum_RoundingTime_30Min", "30分"),
	
	/** The rounding time 60min. */
	ROUNDING_TIME_60MIN(1, "Enum_RoundingTime_60Min", "60分");

	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static Unit[] values = Unit.values();

	/**
	 * Instantiates a new unit.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private Unit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the unit
	 */
	public static Unit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Unit val : Unit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
