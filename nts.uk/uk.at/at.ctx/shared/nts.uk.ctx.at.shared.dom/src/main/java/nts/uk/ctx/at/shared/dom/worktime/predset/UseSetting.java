/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

/**
 * The Enum UseSetting.
 */
public enum UseSetting {

	/** The not use. */
	// 使用しない
	NOT_USE(0, "Enum_UseSetting_NotUse", "使用しない"),

	/** The use. */
	// 使用する
	USE(1, "Enum_UseSetting_Use", "使用する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static UseSetting[] values = UseSetting.values();

	/**
	 * Instantiates a new use setting.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private UseSetting(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the use setting
	 */
	public static UseSetting valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UseSetting val : UseSetting.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
