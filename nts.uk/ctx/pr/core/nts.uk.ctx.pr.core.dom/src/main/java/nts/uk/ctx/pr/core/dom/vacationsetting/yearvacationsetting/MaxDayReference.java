/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

/**
 * The Enum MaxDayReference.
 */
public enum MaxDayReference {
    
	/** The Company uniform. */
	CompanyUniform(0, "会社一律"),

	/** The Refer annual grant table. */
	ReferAnnualGrantTable(1, "年休付与テーブルを参照");

	/** The value. */
	public int value;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static MaxDayReference [] values = MaxDayReference.values();

	/**
	 * Instantiates a new max day reference.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private MaxDayReference(int value, String description) {
		this.value = value;
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
