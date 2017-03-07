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
	MASTER_REF(0, true, false),

	/** The code ref. */
	CODE_REF(1, true, false),

	/** The item data ref. */
	ITEM_DATA_REF(2, false, true),

	/** The experience fix. */
	EXPERIENCE_FIX(3, false, true),

	/** The age fix. */
	AGE_FIX(4, false, true),

	/** The family mem fix. */
	FAMILY_MEM_FIX(5, false, true),

	// Extend element type
	/** The certification. */
	CERTIFICATION(6, true, false),

	/** The working day. */
	WORKING_DAY(7, false, true),

	/** The come late. */
	COME_LATE(8, false, true),

	/** The level. */
	LEVEL(9, true, false);

	/** The value. */
	public int value;

	/** The is code mode. */
	public boolean isCodeMode;

	/** The is range mode. */
	public boolean isRangeMode;

	/** The Constant values. */
	private final static ElementType[] values = ElementType.values();

	/**
	 * Instantiates a new element type.
	 *
	 * @param value
	 *            the value
	 * @param isCodeMode
	 *            the is code mode
	 * @param isRangeMode
	 *            the is range mode
	 */
	private ElementType(int value, boolean isCodeMode, boolean isRangeMode) {
		this.value = value;
		this.isCodeMode = isCodeMode;
		this.isRangeMode = isRangeMode;
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
