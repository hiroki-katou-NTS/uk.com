/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * 上限参照先
 */
public enum MaxDayReference {
    
	/** The Company uniform. */
	CompanyUniform(0, "会社一律", "会社一律"),

	/** The Refer annual grant table. */
	ReferAnnualGrantTable(1, "年休付与テーブルを参照", "年休付与テーブルを参照");

	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static MaxDayReference [] values = MaxDayReference.values();

	/**
	 * Instantiates a new max day reference.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private MaxDayReference(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the max day reference
	 */
	public static MaxDayReference valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MaxDayReference val : MaxDayReference.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
