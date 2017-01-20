/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger;

/**
 * The Enum Category.
 */
public enum WageLedgerCategory {
	
	/** The Salary payment. */
	Payment(1, "支給"),
	
	/** The Salary deduction. */
	Deduction(2, "控除"),
	
	/** The Salary attendance. */
	Attendance(3, "勤怠");
	
	/** The value. */
	public final Integer value;

	/** The description. */
	public final String description;

	/**
	 * Instantiates a new category.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private WageLedgerCategory(Integer value, String description) {
		this.value = value;
		this.description = description;
	}
}
