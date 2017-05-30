/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification;

public interface ClassificationGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	
	/**
	 * Gets the classification code.
	 *
	 * @return the classification code
	 */
	ClassificationCode getClassificationCode();
	
	
	/**
	 * Gets the classification name.
	 *
	 * @return the classification name
	 */
	ClassificationName getClassificationName();
}
