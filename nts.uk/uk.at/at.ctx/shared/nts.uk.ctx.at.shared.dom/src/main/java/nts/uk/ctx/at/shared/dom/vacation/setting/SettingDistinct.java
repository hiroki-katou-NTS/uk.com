/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * The Enum SettingDistinct.
 */
// 設定区分
public enum SettingDistinct {

	/** The yes. */
	YES(1, "設定する", "設定する"),

	/** The no. */
	NO(0, "設定しない", "設定しない");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static SettingDistinct[] values = SettingDistinct.values();

	/**
	 * Instantiates a new setting  distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private SettingDistinct(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the setting distinct
	 */
	public static SettingDistinct valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettingDistinct val : SettingDistinct.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
