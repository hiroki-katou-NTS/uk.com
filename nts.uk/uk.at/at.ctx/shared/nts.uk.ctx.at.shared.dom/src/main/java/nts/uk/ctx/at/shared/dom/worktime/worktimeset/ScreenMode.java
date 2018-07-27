/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Enum ScreenMode.
 */
public enum ScreenMode {

	/** The simple. */
	// 簡易
	SIMPLE(1, "Enum_ScreenMode_Simple", "簡易"),

	/** The detail. */
	// 詳細
	DETAIL(0, "Enum_ScreenMode_Detail", "詳細");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ScreenMode[] values = ScreenMode.values();

	/**
	 * Instantiates a new work time daily atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ScreenMode(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the time day atr
	 */
	public static ScreenMode valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ScreenMode val : ScreenMode.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
