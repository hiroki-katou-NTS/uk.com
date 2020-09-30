/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Enum SettingItemOrder.
 */
// 計算式項目順番
public enum SettingItemOrder {

	/** The left. */
	LEFT(1, "Enum_SettingItemOrder_LEFT", "Item_1"),

	/** The right. */
	RIGHT(2, "Enum_SettingItemOrder_RIGHT", "Item_2");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static SettingItemOrder[] values = SettingItemOrder.values();

	/**
	 * Instantiates a new setting item order.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private SettingItemOrder(int value, String nameId, String description) {
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
	public static SettingItemOrder valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettingItemOrder val : SettingItemOrder.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	
	/**
	 * leftか判定する
	 * @return leftならtrue
	 */
	public boolean isLeft() {
		return LEFT.equals(this);
	}
	
	/**
	 * ｒightか判定する
	 * @return ｒightならtrue
	 */
	public boolean isRight() {
		return RIGHT.equals(this);
	}	
	
	
}
