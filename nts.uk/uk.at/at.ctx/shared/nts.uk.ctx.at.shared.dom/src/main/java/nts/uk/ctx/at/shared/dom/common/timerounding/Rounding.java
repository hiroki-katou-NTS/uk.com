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
	ROUNDING_DOWN(0, "切り捨て", "Enum_Rounding_Down"),

	/** The rounding up. */
	// 切り上げ
	ROUNDING_UP(1, "切り上げ", "Enum_Rounding_Up"),

	/** The rounding down over. */
	// 未満切捨、以上切上
	ROUNDING_DOWN_OVER(2, "未満切捨、以上切上", "Enum_Rounding_Down_Over");

	
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
	
	/**
	 * 自身の逆丸めを取得する
	 * @return 逆丸め設定
	 */
	public Rounding getReverseRounding() {
		switch(this) {
		//切り捨て
		case ROUNDING_DOWN:
			return ROUNDING_UP;
		//未満切り捨て
		case ROUNDING_DOWN_OVER:
			return ROUNDING_DOWN_OVER;
		//切り上げ
		case ROUNDING_UP:
			return ROUNDING_DOWN;
		default:
			throw new RuntimeException("Unknown rounding setting:"+ this);
		}
	}
}
