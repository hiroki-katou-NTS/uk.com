/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Enum DisplayDivision.
 */
public enum DisplayDivision {
    
    /** The Indicate. */
    Indicate(1, "表示する", "表示する"),
    
    /** The Notshow. */
    Notshow(0, "表示しない", "表示しない");

	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static DisplayDivision[] values = DisplayDivision.values();

	/**
	 * Instantiates a new display division.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private DisplayDivision(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the display division
	 */
	public static DisplayDivision valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DisplayDivision val : DisplayDivision.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
