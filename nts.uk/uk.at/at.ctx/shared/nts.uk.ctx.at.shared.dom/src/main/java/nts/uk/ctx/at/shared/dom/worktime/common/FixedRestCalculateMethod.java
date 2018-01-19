/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum FixedRestCalculateMethod.
 */
//固定休憩の計算方法
public enum FixedRestCalculateMethod {

	/** The master ref. */
	// マスタを参照する
	MASTER_REF(0, "Enum_FixedRestCalculateMethod_masterRef", "マスタを参照する"),

	/** The plan ref. */
	// 予定を参照する
	PLAN_REF(1, "Enum_FixedRestCalculateMethod_planRef", "予定を参照する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static FixedRestCalculateMethod[] values = FixedRestCalculateMethod.values();

	/**
	 * Instantiates a new fixed rest calculate method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private FixedRestCalculateMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the fixed rest calculate method
	 */
	public static FixedRestCalculateMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FixedRestCalculateMethod val : FixedRestCalculateMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

	/**
	 * マスタを参照するであるか判定する
	 * @return　マスタ参照である
	 */
	public boolean isReferToMaster() {
		return this.equals(MASTER_REF);
	}
}
