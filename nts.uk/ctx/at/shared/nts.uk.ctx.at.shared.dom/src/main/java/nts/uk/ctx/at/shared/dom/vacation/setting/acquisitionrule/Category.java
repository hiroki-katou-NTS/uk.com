/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

/**
 * The Enum Category.
 */
public enum Category {

	/** The Setting. */
	Setting(0, "設定する", "設定する"),

	/** The No setting. */
	NoSetting(1, "設定しない", "設定しない");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static Category[] values = Category.values();

	/**
	 * Instantiates a new category.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private Category(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the category
	 */
	public static Category valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Category val : Category.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
