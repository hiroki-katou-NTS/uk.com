/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;



/**
 * The Enum ClosureId.
 */
public enum ClosureId {

	/** The Regular employee. */
	RegularEmployee(0, "正社員"),

	/** The Part time job. */
	PartTimeJob(1,"アルバイト"),
	
	/** The Closure three. */
	ClosureThree(2,"締め3"),
	
	/** The Closure four. */
	ClosureFour(3,"締め4"),
	
	/** The Closure five. */
	ClosureFive(4,"締め5");
	

	
	/** The value. */
	public int value;

	/** The description. */
	public String description;
	
	/** The Constant values. */
	private final static ClosureId[] values = ClosureId.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private ClosureId(int value, String description) {
		this.value = value;
		this.description = description;
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