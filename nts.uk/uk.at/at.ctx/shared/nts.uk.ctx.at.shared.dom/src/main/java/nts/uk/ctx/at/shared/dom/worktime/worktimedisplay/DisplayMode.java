/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimedisplay;

/**
 * The Enum DisplayMode.
 */
// 表示モード
public enum DisplayMode {

	/** The detail. */
	// 詳細
	DETAIL(0, "Enum_DisplayMode_Detail", "詳細"),

	/** The simple. */
	// 簡易
	SIMPLE(1, "Enum_DisplayMode_Simple", "簡易");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static DisplayMode[] values = DisplayMode.values();

	/**
	 * Instantiates a new display mode.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private DisplayMode(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the display mode
	 */
	public static DisplayMode valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DisplayMode val : DisplayMode.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

	/**
	 * Checks if is detail.
	 *
	 * @return true, if is detail
	 */
	public boolean isDetail() {
		return DETAIL.equals(this);
	}

	/**
	 * Checks if is simple.
	 *
	 * @return true, if is simple
	 */
	public boolean isSimple() {
		return SIMPLE.equals(this);
	}
}
