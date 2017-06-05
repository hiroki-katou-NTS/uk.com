/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class EmployeeMailAddress.
 */
@StringMaxLength(120)
public class EmployeeMailAddress extends StringPrimitiveValue<EmployeeMailAddress> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employee id.
	 *
	 * @param rawValue the raw value
	 */
	public EmployeeMailAddress(String rawValue) {
		super(rawValue);
	}

}