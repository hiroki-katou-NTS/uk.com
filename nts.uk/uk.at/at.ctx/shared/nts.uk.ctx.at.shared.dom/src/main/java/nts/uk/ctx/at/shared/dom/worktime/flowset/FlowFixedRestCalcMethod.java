/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum FlowFixedRestCalcMethod.
 */
//流動固定休憩の計算方法
public enum FlowFixedRestCalcMethod {
	
	/** The refer master. */
	// マスタを参照する
	REFER_MASTER(0, "Enum_FlowFixedRestCalcMethod_useRestTimeToCalc", "マスタを参照する"),

	/** The stamp whitout refer. */
	//参照せずに打刻する
	STAMP_WHITOUT_REFER(1, "Enum_FlowFixedRestCalcMethod_useGoOutTimeToCalc", "参照せずに打刻する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FlowFixedRestCalcMethod[] values = FlowFixedRestCalcMethod.values();

	/**
	 * Instantiates a new flow fixed rest calc method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private FlowFixedRestCalcMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the flow fixed rest calc method
	 */
	public static FlowFixedRestCalcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FlowFixedRestCalcMethod val : FlowFixedRestCalcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 参照せずに打刻するであるか判定する
	 * @return　参照せずに打刻するである
	 */
	public boolean isStampWithoutReference() {
		return this.equals(STAMP_WHITOUT_REFER);
	}
}
