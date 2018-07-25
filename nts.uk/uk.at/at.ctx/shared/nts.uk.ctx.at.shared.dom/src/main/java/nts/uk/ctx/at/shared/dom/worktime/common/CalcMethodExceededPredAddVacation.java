/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum CalcMethodExceededPredAddVacation.
 */
// 休暇加算時間が所定時間を超過した場合の計算方法
public enum CalcMethodExceededPredAddVacation {

	/** The calc as working. */
	// 就業時間として計算する
	CALC_AS_WORKING(1, "Enum_CalcMethodExceededPredAddVacation_CALC_AS_WORKING", "就業時間として計算する"),

	/** The calc as overtime. */
	// 残業時間として計算する
	CALC_AS_OVERTIME(0, "Enum_CalcMethodExceededPredAddVacation_CALC_AS_OVERTIME", "残業時間として計算する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CalcMethodExceededPredAddVacation[] values = CalcMethodExceededPredAddVacation.values();

	/**
	 * Instantiates a new calc method exceeded pred add vacation.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private CalcMethodExceededPredAddVacation(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the calc method exceeded pred add vacation
	 */
	public static CalcMethodExceededPredAddVacation valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcMethodExceededPredAddVacation val : CalcMethodExceededPredAddVacation.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 就業時間として計算するかどうか判定する
	 * @return　就業時間として判定する
	 */
	public boolean isCalcAsWorking() {
		return CALC_AS_WORKING.equals(this);
	}
	
	/**
	 * 残業時間として計算するかどうか判定する
	 * @return　残業時間として判定する
	 */
	public boolean isCalcAsOverTime() {
		return CALC_AS_OVERTIME.equals(this);
	}
	
}
