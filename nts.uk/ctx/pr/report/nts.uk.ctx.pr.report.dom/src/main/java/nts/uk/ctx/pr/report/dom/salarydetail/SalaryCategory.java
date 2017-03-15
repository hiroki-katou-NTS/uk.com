/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail;

/**
 * The Enum PayrollSummaryCategory.
 */
public enum SalaryCategory {
	
	/** The Payment. */
	Payment(1),
	
	/** The Payment total. */
	PaymentTotal(2),
	
	/** The Deduction. */
	Deduction(3),
	
	/** The Deduction tabulation. */
	DeductionTabulation(4),
	
	/** The Attendance. */
	Attendance(5),
	
	/** The Article others. */
	ArticleOthers(6);
	
	/** The value. */
	public final int value;
	
	/**
	 * Instantiates a new payroll summary category.
	 *
	 * @param value the value
	 */
	private SalaryCategory(int value) {
		this.value = value;
	}
}
