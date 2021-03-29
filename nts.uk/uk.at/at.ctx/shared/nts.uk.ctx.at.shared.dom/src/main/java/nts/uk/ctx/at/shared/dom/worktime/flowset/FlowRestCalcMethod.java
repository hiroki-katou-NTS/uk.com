/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Enum FlowRestCalcMethod.
 */
// 流動休憩の計算方法
public enum FlowRestCalcMethod {

	/** The refer master. */
	// マスタを参照する
	REFER_MASTER(0, "Enum_FlowRestCalcMethod_useRestTimeToCalc", "マスタを参照する"),

	/** The use master and stamp. */
	// マスタと打刻を併用する
	USE_MASTER_AND_STAMP(1, "Enum_FlowRestCalcMethod_useGoOutTimeToCalc", "マスタと打刻を併用する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FlowRestCalcMethod[] values = FlowRestCalcMethod.values();

	/**
	 * Instantiates a new flow rest calc method.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private FlowRestCalcMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the flow rest calc method
	 */
	public static FlowRestCalcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FlowRestCalcMethod val : FlowRestCalcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	public boolean isUseMasterAndStamp() {
		return this == FlowRestCalcMethod.USE_MASTER_AND_STAMP;
	}

}
