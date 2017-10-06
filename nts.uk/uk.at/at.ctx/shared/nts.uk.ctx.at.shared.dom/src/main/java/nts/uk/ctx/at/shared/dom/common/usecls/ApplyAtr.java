/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.usecls;

/**
 * The Enum UseAtr.
 */
// <<boolean>> 使用区分
public enum ApplyAtr {

	/** The use. */
	// 使用する
	USE(1, "ENUM_APPLYATR_USE"),

	/** The not use. */
	// 使用しない
	NOT_USE(0, "ENUM_APPLYATR_NOT_USE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static ApplyAtr[] values = ApplyAtr.values();

	/**
	 * Instantiates a new use atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private ApplyAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use atr
	 */
	public static ApplyAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ApplyAtr val : ApplyAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
