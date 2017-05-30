/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.category;

import java.util.List;

public interface ManagementCategoryRepository {
	
	/**
	 * Adds the.
	 *
	 * @param managementCategory the management category
	 */
	void add(ManagementCategory managementCategory);
	
	/**
	 * Update.
	 *
	 * @param managementCategory the management category
	 */
	void update(ManagementCategory managementCategory);
	
	
	
	/**
	 * Gets the all management category.
	 *
	 * @param companyId the company id
	 * @return the all management category
	 */
	List<ManagementCategory> getAllManagementCategory(String companyId);

}
