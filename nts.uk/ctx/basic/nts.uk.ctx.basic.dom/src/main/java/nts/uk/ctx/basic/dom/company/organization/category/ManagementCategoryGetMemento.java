/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.category;

public interface ManagementCategoryGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	
	/**
	 * Gets the management category code.
	 *
	 * @return the management category code
	 */
	ManagementCategoryCode getManagementCategoryCode();
	
	
	/**
	 * Gets the management category name.
	 *
	 * @return the management category name
	 */
	ManagementCategoryName getManagementCategoryName();
}
