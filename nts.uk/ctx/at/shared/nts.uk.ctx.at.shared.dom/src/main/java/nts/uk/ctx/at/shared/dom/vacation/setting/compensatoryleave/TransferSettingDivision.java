/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Enum TransferSettingDivision.
 */
public enum TransferSettingDivision {
	
	/** The Design time. */
	DesignTime(0,"指定した時間を代休とする","指定した時間を代休とする"),
	
	/** The Certain time. */
	CertainTime(1,"一定時間を超えたら代休とする","一定時間を超えたら代休とする");
	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TransferSettingDivision[] values = TransferSettingDivision.values();

	/**
	 * Instantiates a new manage distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private TransferSettingDivision(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the manage distinct
	 */
	public static TransferSettingDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TransferSettingDivision val : TransferSettingDivision.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
