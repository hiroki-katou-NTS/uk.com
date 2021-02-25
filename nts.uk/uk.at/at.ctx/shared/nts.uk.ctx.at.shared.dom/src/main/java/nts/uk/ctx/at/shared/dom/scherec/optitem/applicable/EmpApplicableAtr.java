/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

/**
 * The Enum EmpApplicableAtr.
 */
// 雇用適用区分
public enum EmpApplicableAtr {

	/** The not apply. */
	// 適用しない
	NOT_APPLY(0, "Enum_EmpApplicableAtr_NOT_APPLY", "適用しない"),

	/** The apply. */
	// 適用する
	APPLY(1, "Enum_EmpApplicableAtr_APPLY", "適用する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static EmpApplicableAtr[] values = EmpApplicableAtr.values();

	/**
	 * Instantiates a new emp applicable atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private EmpApplicableAtr(int value, String nameId, String description) {
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
	public static EmpApplicableAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EmpApplicableAtr val : EmpApplicableAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
