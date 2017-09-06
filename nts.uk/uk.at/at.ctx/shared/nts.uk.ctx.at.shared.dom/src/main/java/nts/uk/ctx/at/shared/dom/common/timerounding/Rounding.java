/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.timerounding;

/**
 * The Enum Rounding.
 */
// 端数処理
public enum Rounding {
	
	/** The rounding down. */
	// 切り捨て
	ROUNDING_DOWN(0, "Enum_Rounding_Down", "切り捨て"),
	
	/** The rounding up. */
	//切り上げ
	ROUNDING_UP(1, "Enum_Rounding_Up", "切り上げ"),
	
	/** The rounding down over. */
	//未満切捨、以上切上
	ROUNDING_DOWN_OVER(2, "Enum_Rounding_Down_Over", "未満切捨、以上切上");

	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static Rounding[] values = Rounding.values();

	/**
	 * Instantiates a new unit.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private Rounding(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the rounding
	 */
	public static Rounding valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Rounding val : Rounding.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
