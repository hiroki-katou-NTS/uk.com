/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime;

/**
 * The Enum OvertimeCalculationMethod.
 */
// 時間外超過計算方法
public enum OvertimeCalculationMethod {
	
	/** The time series. */
	// 時系列（原則）
	TIME_SERIES(0, "Enum_OvertimeCalculationMethod_Time_Series", "時系列（原則）"),
	
	/** The decision after. */
	//集計後に判断（便宜上）
	DECISION_AFTER(1, "Enum_OvertimeCalculationMethod_Decision_After", "集計後に判断（便宜上）");

	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static OvertimeCalculationMethod[] values = OvertimeCalculationMethod.values();

	/**
	 * Instantiates a new overtime calculation method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private OvertimeCalculationMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the overtime calculation method
	 */
	public static OvertimeCalculationMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OvertimeCalculationMethod val : OvertimeCalculationMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
