/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

/**
 * The Enum UseAtr.
 */
public enum UseAtr {

	NotUse(0, "使用しない", "Enum_UseAtr_NotUse"),

	Use(1, "使用する", "Enum_UseAtr_Use");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static UseAtr[] values = UseAtr.values();

	/**
	 * Instantiates a new work type atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private UseAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the summary atr
	 */
	public static UseAtr valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (UseAtr val : UseAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
