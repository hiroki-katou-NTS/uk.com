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
	Payment(1),
	
	/** The Salary deduction. */
	Deduction(2),
	
	/** The Salary attendance. */
	Attendance(3);
	
	/** The value. */
	public final Integer value;

	/**
	 * Instantiates a new category.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private WLCategory(Integer value) {
		this.value = value;
	}
}
