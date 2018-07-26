/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum GoOutTimeRoundingMethod.
 */
//外出時間の丸め方法
public enum GoOutTimeRoundingMethod {

	/** The total and rounding. */
	// 合計してから丸める
	TOTAL_AND_ROUNDING(0, "Enum_GoOutTimeRoundingMethod_totalAndRounding", "合計してから丸める"),

	/** The rounding and total. */
	// 休憩時間帯毎に丸めてから合計
	ROUNDING_AND_TOTAL(1, "Enum_GoOutTimeRoundingMethod_roundingAndTotal", "休憩時間帯毎に丸めてから合計");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static GoOutTimeRoundingMethod[] values = GoOutTimeRoundingMethod.values();

	/**
	 * Instantiates a new go out time rounding method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private GoOutTimeRoundingMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the go out time rounding method
	 */
	public static GoOutTimeRoundingMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GoOutTimeRoundingMethod val : GoOutTimeRoundingMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 合計してから丸めるか判定する
	 * @return　合計してから丸める
	 */
	public boolean isTotalAndRounding() {
		return TOTAL_AND_ROUNDING.equals(this);
	}
	
	/**
	 * 休憩時間帯毎に丸めてから合計か判定する
	 * @return　休憩時間帯毎に丸めてから合計
	 */
	public boolean isRoundingAndTotal() {
		return ROUNDING_AND_TOTAL.equals(this);
	}
	
}
