/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.wagetable;

/**
 * The Enum ElementType.
 */
public enum ElementType {

	/** The master ref. */
	MASTER_REF(0),

	/** The code ref. */
	CODE_REF(1),

	/** The item data ref. */
	ITEM_DATA_REF(2),

	/** The experience fix. */
	EXPERIENCE_FIX(3),

	/** The age fix. */
	AGE_FIX(4),

	/** The family mem fix. */
	FAMILY_MEM_FIX(5),

	// Extend element type
	/** The certification. */
	CERTIFICATION(6),

	/** The working days. */
	WORKING_DAY(7),

	/** The come late. */
	COME_LATE(8),

	/** The level. */
	LEVEL(9);

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
