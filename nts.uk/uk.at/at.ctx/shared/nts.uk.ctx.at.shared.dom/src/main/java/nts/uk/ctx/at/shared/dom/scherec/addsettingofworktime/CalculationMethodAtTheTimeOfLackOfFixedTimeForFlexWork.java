/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork.
 */
// フレックス勤務の所定時間不足時の計算方法
public enum CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork {
	
	/** The calculate deficiency of flex. */
	// フレックス不足を計算する
	CALCULATE_DEFICIENCY_OF_FLEX(0, "Enum_CalculateDeficiencyOfFlex"),
	
	/** The do not calculate flex deficiency. */
	// フレックス不足を計算しない
	DO_NOT_CALCULATE_FLEX_DEFICIENCY(1, "Enum_doNotCalcFlexDeficiency");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork[] values = CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork.values();

	
	/**
	 * Instantiates a new calculation method at the time of lack of fixed time for flex work.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the calculation method at the time of lack of fixed time for flex work
	 */
	public static CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork val : CalculationMethodAtTheTimeOfLackOfFixedTimeForFlexWork.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	public boolean isCalc() {
		return this.equals(CALCULATE_DEFICIENCY_OF_FLEX);
	}
}

