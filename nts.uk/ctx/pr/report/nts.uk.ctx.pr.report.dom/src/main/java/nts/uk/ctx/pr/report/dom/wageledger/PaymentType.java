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
	Salary(1, "Salary"),
	
	/** The Bonus. */
	Bonus(2, "Bonus");
	
	/** The value. */
	public final Integer value;
	
	/** The name. */
	public final String name;
	
	/**
	 * Instantiates a new payment type.
	 *
	 * @param value the value
	 */
	private PaymentType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Value of name.
	 *
	 * @param name the name
	 * @return the payment type
	 */
	public static PaymentType valueOfName(String name) {
		// Invalid object.
		if (name == null) {
			return null;
		}

		// Find value.
		for (PaymentType val : PaymentType.values()) {
			if (val.name == name) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
