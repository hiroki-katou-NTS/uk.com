/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Enum TransferSettingDivision.
 */
public enum TransferSettingDivision {
	
	/** The Certain time. */
	CertainTime(1),
	
	/** The Design time. */
	DesignTime(0);
	
	/** The value. */
	public int value;
	
	/**
	 * Instantiates a new transfer setting division.
	 *
	 * @param value the value
	 */
	private TransferSettingDivision(int value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the transfer setting division
	 */
	public static TransferSettingDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TransferSettingDivision val : TransferSettingDivision.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
