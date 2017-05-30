/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ClassificationName.
 */
@StringMaxLength(20)
public class ManagementCategoryName extends StringPrimitiveValue<ManagementCategoryName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8807988305434200565L;

	/**
	 * Instantiates a new management category name.
	 *
	 * @param rawValue the raw value
	 */
	public ManagementCategoryName(String rawValue) {
		super(rawValue);
	}

}
