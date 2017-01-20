/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger;

/**
 * The Enum PaymentType.
 */
public enum PaymentType {
	
	/** The Salary. */
	Salary(1),
	
	/** The Bonus. */
	Bonus(2);
	
	/** The value. */
	public final Integer value;
	
	/**
	 * Instantiates a new payment type.
	 *
	 * @param value the value
	 */
	private PaymentType(Integer value) {
		this.value = value;
	}
}
