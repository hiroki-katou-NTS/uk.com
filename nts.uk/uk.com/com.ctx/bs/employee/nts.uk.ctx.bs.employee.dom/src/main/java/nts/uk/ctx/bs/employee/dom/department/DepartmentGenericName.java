/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.department;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class DepartmentGenericName.
 */
@StringMaxLength(40)
public class DepartmentGenericName extends StringPrimitiveValue<DepartmentGenericName>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new department generic name.
	 *
	 * @param rawValue the raw value
	 */
	public DepartmentGenericName(String rawValue) {
		super(rawValue);
	}

}
