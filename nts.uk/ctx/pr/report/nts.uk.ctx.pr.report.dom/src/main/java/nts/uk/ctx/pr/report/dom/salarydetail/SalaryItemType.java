/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail;

/**
 * The Enum PaymentSummaryType.
 */
public enum SalaryItemType {
	
	/** The Aggregate. */
	Aggregate(1),
	
	/** The Master. */
	Master(2);
	
	/** The value. */
	public final int value;
	
	/**
	 * Instantiates a new payment summary type.
	 *
	 * @param value the value
	 */
	private SalaryItemType(int value) {
		this.value = value;
	}
}
