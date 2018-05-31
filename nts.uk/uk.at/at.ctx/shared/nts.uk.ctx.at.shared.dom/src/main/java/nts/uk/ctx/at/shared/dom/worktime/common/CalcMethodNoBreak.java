/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum CalcMethodNoBreak.
 */
// 休憩未取得時の計算方法
public enum CalcMethodNoBreak {

	/** The calc as working. */
	// 就業時間として計算
	CALC_AS_WORKING(1, "Enum_CalcMethodNoBreak_CALC_AS_WORKING", "就業時間として計算"),

	/** The calc as overtime. */
	// 残業時間として計算
	CALC_AS_OVERTIME(0, "Enum_CalcMethodNoBreak_CALC_AS_OVERTIME", "残業時間として計算");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CalcMethodNoBreak[] values = CalcMethodNoBreak.values();

	/**
	 * Instantiates a new calc method no break.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private CalcMethodNoBreak(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the calc method no break
	 */
	public static CalcMethodNoBreak valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcMethodNoBreak val : CalcMethodNoBreak.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	
	/**
	 * 就業時間から計算であるか判定する
	 * @return 就業時間から計算である
	 */
	public boolean isCalcAsWorking() {
		return CALC_AS_WORKING.equals(this);
	}
}
