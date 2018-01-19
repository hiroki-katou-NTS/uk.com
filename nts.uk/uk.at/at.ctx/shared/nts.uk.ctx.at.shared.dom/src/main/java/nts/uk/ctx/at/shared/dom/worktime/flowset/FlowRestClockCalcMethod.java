/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum FlowRestClockCalcMethod.
 */
//流動休憩打刻併用時の計算方法
public enum FlowRestClockCalcMethod {

	/** The use rest time to calculate. */
	// 休憩として扱う外出を休憩時間として計算する
	USE_REST_TIME_TO_CALCULATE(0, "Enum_FlowRestClockCalcMethod_useRestTimeToCalc", "休憩として扱う外出を休憩時間として計算する"),

	/** The use goout time to calculate. */
	// 休憩として扱う外出を外出時間として計算する
	USE_GOOUT_TIME_TO_CALCULATE(1, "Enum_FlowRestClockCalcMethod_useGoOutTimeToCalc", "休憩として扱う外出を外出時間として計算する");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FlowRestClockCalcMethod[] values = FlowRestClockCalcMethod.values();

	/**
	 * Instantiates a new flow rest clock calc method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private FlowRestClockCalcMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the flow rest clock calc method
	 */
	public static FlowRestClockCalcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FlowRestClockCalcMethod val : FlowRestClockCalcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
