/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.wagetable;

/**
 * The Enum ElementType.
 */
public enum ElementType {

	/** The Master ref. */
	MasterRef(0),

	/** The Code ref. */
	CodeRef(1),

	/** The Item data ref. */
	ItemDataRef(2),

	/** The Experience fix. */
	ExperienceFix(3),

	/** The Age fix. */
	AgeFix(4),

	/** The Family mem fix. */
	FamilyMemFix(5);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ElementType[] values = ElementType.values();

	/**
	 * Instantiates a new element type.
	 *
	 * @param value
	 *            the value
	 */
	private ElementType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the element type
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
