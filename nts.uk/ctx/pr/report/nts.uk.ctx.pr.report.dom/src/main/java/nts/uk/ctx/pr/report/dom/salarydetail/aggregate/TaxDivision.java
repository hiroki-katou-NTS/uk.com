/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

/**
 * The Enum TaxDivision.
 */
public enum TaxDivision {
	
	/** The Payment. */
	Payment(1),
	
	/** The Deduction. */
	Deduction(2);
	
	/** The value. */
	public final int value;
	
	/**
	 * Instantiates a new tax division.
	 *
	 * @param value the value
	 */
	private TaxDivision(int value) {
		this.value = value;
	}
}
