/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime.
 */
// フレックス勤務の所定時間超過時の計算方法
public enum CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime {
	
	/** The calculate flex excess. */
	// フレックス超過を計算する
	CALCULATE_FLEX_EXCESS(0, "Enum_calculateFlexExcess"),
	
	/** The do not calculate flex excess. */
	// フレックス超過を計算しない
	DO_NOT_CALCULATE_FLEX_EXCESS(1, "Enum_DoNotCalcFlexExcess");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime[] values = CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime.values();

	
	/**
	 * Instantiates a new calulation method when flex work hours exceeded the prescribed time.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the calulation method when flex work hours exceeded the prescribed time
	 */
	public static CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime val : CalulationMethodWhenFlexWorkHoursExceededThePrescribedTime.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

