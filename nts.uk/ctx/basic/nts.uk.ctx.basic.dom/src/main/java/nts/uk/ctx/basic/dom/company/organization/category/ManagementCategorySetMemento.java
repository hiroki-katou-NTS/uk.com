/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.category;

/**
 * The Interface ClassificationSetMemento.
 */
public interface ManagementCategorySetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	
	/**
	 * Sets the management category code.
	 *
	 * @param managementCategoryCode the new management category code
	 */
	void setManagementCategoryCode(ManagementCategoryCode managementCategoryCode);
	
	
	/**
	 * Sets the management category name.
	 *
	 * @param managementCategoryName the new management category name
	 */
	void setManagementCategoryName(ManagementCategoryName managementCategoryName);
}
