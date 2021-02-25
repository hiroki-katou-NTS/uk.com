/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Enum SettingMethod.
 */
// 設定方法
public enum SettingMethod {

	/** The item selection. */
	// 項目選択
	ITEM_SELECTION(0, "Enum_SettingMethod_ITEM_SELECTION", "項目選択"),

	/** The numerical input. */
	// 数値入力
	NUMERICAL_INPUT(1, "Enum_SettingMethod_NUMERICAL_INPUT", "数値入力");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static SettingMethod[] values = SettingMethod.values();

	/**
	 * Instantiates a new setting method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private SettingMethod(int value, String nameId, String description) {
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
	public static SettingMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettingMethod val : SettingMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 項目選択か判定する
	 * @return leftならtrue
	 */
	public boolean isItemSelection() {
		return ITEM_SELECTION.equals(this);
	}
	
	/**
	 * 数値入力か判定する
	 * @return ｒightならtrue
	 */
	public boolean isNumberInput() {
		return NUMERICAL_INPUT.equals(this);
	}	
}
