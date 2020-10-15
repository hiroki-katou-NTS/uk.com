/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

/**
 * The Enum OutsideOTCalMed.
 */
// 時間外超過計算方法
public enum OutsideOTCalMed {
	
	/** The time series. */
	// 時系列（原則）
	TIME_SERIES(0, "時系列（原則）", "Enum_OutsideOTCalMed_Time_Series"),
	
	/** The decision after. */
	//集計後に判断（便宜上）
	DECISION_AFTER(1, "集計後に判断（便宜上）", "Enum_OutsideOTCalMed_Decision_After");

	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static OutsideOTCalMed[] values = OutsideOTCalMed.values();

	/**
	 * Instantiates a new overtime calculation method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private OutsideOTCalMed(int value, String nameId, String description) {
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
	public static OutsideOTCalMed valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OutsideOTCalMed val : OutsideOTCalMed.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
