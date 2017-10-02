/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting;

/**
 * The Enum UseClassification.
 */
// 使用区分
public enum UseClassification {

	/** The not use. */
	// 使用しない
	NOT_USE(0, "使用しない", "Enum_UseClassification_Not_Use"),

	/** The use. */
	// 使用する
	USE(1, "使用する", "Enum_UseClassification_Use");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static UseClassification[] values = UseClassification.values();

	/**
	 * Instantiates a new use classification.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private UseClassification(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the am pm classification
	 */
	public static UseClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UseClassification val : UseClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
