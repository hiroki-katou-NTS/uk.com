/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum CalculationMethodForNormalWorkAndDeformedLaborOverTime.
 */
// 通常勤務、変形労働の所定時間超過時の計算方法
public enum CalculationMethodForNormalWorkAndDeformedLaborOverTime {
	
	/** The calculate as working hours. */
	// 就業時間として計算する
	CALCULATE_AS_WORKING_HOURS(0, "Enum_CalculateAsWorkingHours"),
	
	/** The calculate as overtime hours. */
	// 残業時間として計算する
	CALCULATE_AS_OVERTIME_HOURS(1, "Enum_CalculateAsOvertimeHours");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static CalculationMethodForNormalWorkAndDeformedLaborOverTime[] values = CalculationMethodForNormalWorkAndDeformedLaborOverTime.values();

	
	/**
	 * Instantiates a new calculation method for normal work and deformed labor over time.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private CalculationMethodForNormalWorkAndDeformedLaborOverTime(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the calculation method for normal work and deformed labor over time
	 */
	public static CalculationMethodForNormalWorkAndDeformedLaborOverTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalculationMethodForNormalWorkAndDeformedLaborOverTime val : CalculationMethodForNormalWorkAndDeformedLaborOverTime.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

