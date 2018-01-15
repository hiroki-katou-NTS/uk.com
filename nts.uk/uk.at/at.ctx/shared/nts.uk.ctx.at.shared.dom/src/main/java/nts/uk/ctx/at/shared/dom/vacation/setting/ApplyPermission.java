/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting;

/**
 * The Enum ApplyPermission.
 */
// 許可区分
public enum ApplyPermission {

	// 許可する
	ALLOW(1, "許可する", "許可する"),

	// 許可しない
	NOT_ALLOW(0, "許可しない", "許可しない");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static ApplyPermission[] values = ApplyPermission.values();

	/**
	 * Instantiates a new manage distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ApplyPermission(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the manage distinct
	 */
	public static ApplyPermission valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ApplyPermission val : ApplyPermission.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
