/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

/**
 * The Enum CalculationRangeCheck.
 */
// 計算範囲チェック
public enum CalcRangeCheck {

	/** The not set. */
	// 設定しない
	NOT_SET(0, "Enum_CalcRangeCheck_NOT_SET", "設定しない"),

	/** The set. */
	// 設定する
	SET(1, "Enum_CalcRangeCheck_SET", "設定する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static CalcRangeCheck[] values = CalcRangeCheck.values();

	/**
	 * Instantiates a new calc range check.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CalcRangeCheck(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the emp condition atr
	 */
	public static CalcRangeCheck valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcRangeCheck val : CalcRangeCheck.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 設定するか判定する
	 * @return 設定するならtrue
	 */
	public boolean isSET() {
		return SET.equals(this);
	}
	

}
