/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Enum AddSubOperator.
 */
// 加減算演算子
public enum AddSubOperator {

	/** The add. */
	ADD(0, "Enum_AddSubOperator_ADD", "+"),

	/** The subtract. */
	SUBTRACT(1, "Enum_AddSubOperator_SUBTRACT", "-");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AddSubOperator[] values = AddSubOperator.values();

	/**
	 * Instantiates a new adds the sub operator.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private AddSubOperator(int value, String nameId, String description) {
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
	public static AddSubOperator valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AddSubOperator val : AddSubOperator.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
