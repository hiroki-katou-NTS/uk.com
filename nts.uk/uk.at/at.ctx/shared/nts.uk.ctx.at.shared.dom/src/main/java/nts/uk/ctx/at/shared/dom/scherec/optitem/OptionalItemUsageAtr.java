/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

/**
 * The Enum OptionalItemUsageAtr.
 */
// 任意項目利用区分
public enum OptionalItemUsageAtr {

	/** The not use. */
	// 利用しない
	NOT_USE(0, "Enum_OptionalItemUsageAtr_NOT_USE", "利用しない"),

	/** The use. */
	// 利用する
	USE(1, "Enum_OptionalItemUsageAtr_USE", "利用する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static OptionalItemUsageAtr[] values = OptionalItemUsageAtr.values();

	/**
	 * Instantiates a new optional item usage atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private OptionalItemUsageAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the optional item usage atr
	 */
	public static OptionalItemUsageAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OptionalItemUsageAtr val : OptionalItemUsageAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 利用するか判定する
	 * @return するならtrue
	 */
	public boolean isUSE() {
		return USE.equals(this);
	}
	
	/**
	 * 利用しないか判定する
	 * @return しないならtrue
	 */
	public boolean isNotUse() {
		return NOT_USE.equals(this);
	}

}
