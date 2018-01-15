/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

//廃止区分
/**
 * The Enum AbolishAtr.
 */
public enum AbolishAtr {

	/** The abolish. */
	// 廃止する
	ABOLISH(0, "Enum_AbolishAtr_Abolish", "廃止する"),

	/** The not abolish. */
	// 廃止しない
	NOT_ABOLISH(1, "Enum_AbolishAtr_Not_Abolish", "廃止しない");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static AbolishAtr[] values = AbolishAtr.values();

	/**
	 * Instantiates a new abolish atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private AbolishAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the abolish atr
	 */
	public static AbolishAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AbolishAtr val : AbolishAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
