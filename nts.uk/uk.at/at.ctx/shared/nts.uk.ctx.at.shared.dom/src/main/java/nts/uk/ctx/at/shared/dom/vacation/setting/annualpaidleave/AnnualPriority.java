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
	FIFO(0, "当年付与分から消化する", "当年付与分から消化する"),

	/** The lifo. */
	LIFO(1, "繰越分から消化する", "繰越分から消化する");

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
