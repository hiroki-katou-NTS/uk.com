/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;


/**
 * The Enum OvertimeNo.
 */
// 超過時間NO
public enum OvertimeNo {
	
	/** The one. */
	ONE(1, "ONE"),
	
	/** The two. */
	TWO(2, "TWO"),
	
	/** The three. */
	THREE(3, "THREE"),
	
	/** The four. */
	FOUR(4, "FOUR"),
	
	/** The five. */
	FIVE(5, "FIVE");

	/** The value. */
	public int value;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static OvertimeNo[] values = OvertimeNo.values();

	/**
	 * Instantiates a new overtime no.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private OvertimeNo(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the estimate term of use
	 */
	public static OvertimeNo valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OvertimeNo val : OvertimeNo.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
