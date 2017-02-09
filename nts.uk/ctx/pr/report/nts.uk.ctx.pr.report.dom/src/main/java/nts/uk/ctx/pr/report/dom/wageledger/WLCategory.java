/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger;

/**
 * The Enum Category.
 */
public enum WLCategory {
	
	/** The Salary payment. */
	Payment(1, "Payment"),
	
	/** The Salary deduction. */
	Deduction(2, "Deduction"),
	
	/** The Salary attendance. */
	Attendance(3, "Attendance");
	
	/** The value. */
	public final Integer value;

	/** The description. */
	public final String name;

	/**
	 * Instantiates a new category.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private WLCategory(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static WLCategory valueOfName(String name) {
		// Invalid object.
		if (name == null) {
			return null;
		}

		// Find value.
		for (WLCategory val : WLCategory.values()) {
			if (val.name == name) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
