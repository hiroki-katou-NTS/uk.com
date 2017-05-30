/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification;

/**
 * The Interface ClassificationSetMemento.
 */
public interface ClassificationSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	
	/**
	 * Sets the classification code.
	 *
	 * @param classificationCode the new classification code
	 */
	void setClassificationCode(ClassificationCode classificationCode);
	
	
	/**
	 * Sets the classification name.
	 *
	 * @param classificationName the new classification name
	 */
	void setClassificationName(ClassificationName classificationName);
}
