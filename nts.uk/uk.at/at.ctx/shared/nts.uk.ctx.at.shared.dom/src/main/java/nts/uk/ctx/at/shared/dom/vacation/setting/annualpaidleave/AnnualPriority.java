/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Enum PreemptionPermit.
 */
// 年休消化優先
public enum AnnualPriority {

	/** The fifo. */
	FIFO(0, "先入れ先出し", "先入れ先出し"),

	/** The lifo. */
	LIFO(1, "後入れ先出し", "後入れ先出し");

	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AnnualPriority[] values = AnnualPriority.values();

	/**
	 * Instantiates a new preemption permit.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private AnnualPriority(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the preemption permit
	 */
	public static AnnualPriority valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AnnualPriority val : AnnualPriority.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
