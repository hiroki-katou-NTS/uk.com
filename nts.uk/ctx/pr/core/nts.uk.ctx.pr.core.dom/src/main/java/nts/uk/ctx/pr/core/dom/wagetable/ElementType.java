/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.wagetable;

/**
 * The Enum MultipleTargetSetting.
 */
public enum ElementType {

	/** The Bigest method. */
	MasterRef(0),

	/** The Total method. */
	CodeRef(1),

	/** The Three. */
	ItemDataRef(2),

	/** The Eligibility. */
	ExperienceFix(3),

	/** The Finework. */
	AgeFix(4),

	/** The Family mem fix. */
	FamilyMemFix(5);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ElementType[] values = ElementType.values();

	/**
	 * Instantiates a new multiple target setting.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ElementType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the multiple target setting
	 */
	public static ElementType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ElementType val : ElementType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
