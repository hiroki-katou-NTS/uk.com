/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;



/**
 * The Enum ClosureId.
 */
public enum ClosureId {

	/** The Regular employee. */
	RegularEmployee(1),

	/** The Part time job. */
	PartTimeJob(2),
	
	/** The Closure three. */
	ClosureThree(3),
	
	/** The Closure four. */
	ClosureFour(4),
	
	/** The Closure five. */
	ClosureFive(5);

	
	/** The value. */
	public int value;
	
	/** The Constant values. */
	private final static ClosureId[] values = ClosureId.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private ClosureId(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the use division
	 */
	public static ClosureId valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ClosureId val : ClosureId.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}