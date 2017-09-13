/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;

public interface ClassificationGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	
	/**
	 * Gets the management category code.
	 *
	 * @return the management category code
	 */
	ClassificationCode getClassificationCode();
	
	
	/**
	 * Gets the management category name.
	 *
	 * @return the management category name
	 */
	ClassificationName getClassificationName();
}
