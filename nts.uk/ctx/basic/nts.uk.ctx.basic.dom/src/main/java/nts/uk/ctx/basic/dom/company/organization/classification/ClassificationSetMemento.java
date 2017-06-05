/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification;

import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface ClassificationSetMemento.
 */
public interface ClassificationSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the management category code.
	 *
	 * @param managementCategoryCode the new management category code
	 */
	void setManagementCategoryCode(ClassificationCode managementCategoryCode);
	
	
	/**
	 * Sets the management category name.
	 *
	 * @param managementCategoryName the new management category name
	 */
	void setManagementCategoryName(ClassificationName managementCategoryName);
}
