/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Enum OperatorClassification.
 */
// 演算子区分
public enum OperatorAtr {

	/** The add. */
	ADD(0, "Enum_OperatorAtr_ADD", "+"),

	/** The subtract. */
	SUBTRACT(1, "Enum_OperatorAtr_SUBTRACT", "-"),

	/** The multiply. */
	MULTIPLY(2, "Enum_OperatorAtr_MULTIPLY", "*"),

	/** The divide. */
	DIVIDE(3, "Enum_OperatorAtr_DIVIDE", "/");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static OperatorAtr[] values = OperatorAtr.values();

	/**
	 * Instantiates a new operator atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private OperatorAtr(int value, String nameId, String description) {
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
	public static OperatorAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OperatorAtr val : OperatorAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
