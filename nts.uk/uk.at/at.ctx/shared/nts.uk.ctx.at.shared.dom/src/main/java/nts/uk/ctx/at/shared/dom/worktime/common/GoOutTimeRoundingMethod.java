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
	
	/** 実働時間帯の枠ごとに合計せず丸める */
	IN_FRAME(0, "Enum_GoOutTimeRoundingMethod_InFrame", "外出時間帯ごとに丸める"),
	
	/** 実働時間帯の枠ごとに合計してから丸める */
	AFTER_TOTAL_IN_FRAME(1, "Enum_GoOutTimeRoundingMethod_AfterTotalInFrame", "実働時間帯の枠ごとに合計してから丸める"),

	/** 実働時間帯ごとに合計して丸める */
	AFTER_TOTAL(2, "Enum_GoOutTimeRoundingMethod_AfterTotal", "実働時間帯ごとに合計して丸める");

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
	 * 実働時間帯の枠ごとに合計せず丸めるか
	 */
	public boolean isRoundingGoOut() {
		return IN_FRAME.equals(this);
	}
	
	/**
	 * 実働時間帯の枠ごとに合計してから丸めるか
	 */
	public boolean isRoundinAfterTotalInFrame() {
		return AFTER_TOTAL_IN_FRAME.equals(this);
	}
	
	/**
	 * 実働時間帯ごとに合計して丸めるか
	 */
	public boolean isRoundingAfterTotal() {
		return AFTER_TOTAL.equals(this);
	}
}
