/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum FontRearSection.
 */
//前後区分
public enum FontRearSection {
	
	/** The before. */
	// 前
	BEFORE(0, "Enum_FontRearSection_before", "前"),

	/** The after. */
	// 後
	AFTER(1, "Enum_FontRearSection_after", "後");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FontRearSection[] values = FontRearSection.values();

	/**
	 * Instantiates a new font rear section.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private FontRearSection(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the font rear section
	 */
	public static FontRearSection valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FontRearSection val : FontRearSection.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
