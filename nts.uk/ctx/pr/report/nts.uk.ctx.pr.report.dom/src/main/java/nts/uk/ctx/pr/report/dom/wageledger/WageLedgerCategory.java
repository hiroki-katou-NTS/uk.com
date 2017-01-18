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
	SalaryPayment(1, "salary payment"),
	
	/** The Salary deduction. */
	SalaryDeduction(2, "salary deduction"),
	
	/** The Salary attendance. */
	SalaryAttendance(3, "salary attendance"),
	
	/** The Bonus payment. */
	BonusPayment(4, "bonus payment"),
	
	/** The Bonus deduction. */
	BonusDeduction(5, "bonus deduction"),
	
	/** The Bonus attendance. */
	BonusAttendance(6, "bonus attendance");
	
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
