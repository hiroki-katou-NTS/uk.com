/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum PrePlanWorkTimeCalcMethod.
 */
// 予定前出勤時の計算方法
public enum PrePlanWorkTimeCalcMethod {

	/** The not change. */
	// 予定開始時刻から計算する
	CALC_FROM_PLAN_START_TIME(0, "Enum_FixedChangeAtr_calcFromPlanStartTime", "予定開始時刻から計算する"),

	/** The after shift. */
	// 後にズラす
	CALC_FROM_WORK_TIME(1, "Enum_FixedChangeAtr_calcFromWorkTime", "出勤時刻から計算する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static PrePlanWorkTimeCalcMethod[] values = PrePlanWorkTimeCalcMethod.values();

	/**
	 * Instantiates a new fixed change atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private PrePlanWorkTimeCalcMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the fixed change atr
	 */
	public static PrePlanWorkTimeCalcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PrePlanWorkTimeCalcMethod val : PrePlanWorkTimeCalcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 予定開始時刻から計算する
	 * @return true：予定開始時刻から計算する  false：出勤時刻から計算する
	 */
	public boolean isCalcFromPlanStartTime() {
		return this.equals(CALC_FROM_PLAN_START_TIME);
	}
}
