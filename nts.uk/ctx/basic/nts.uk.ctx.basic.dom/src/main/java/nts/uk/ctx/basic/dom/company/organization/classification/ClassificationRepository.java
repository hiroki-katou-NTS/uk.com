/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.classification;

import java.util.List;

public interface ClassificationRepository {
	
	/**
	 * Adds the.
	 *
	 * @param managementCategory the management category
	 */
	void add(Classification managementCategory);
	
	/**
	 * Update.
	 *
	 * @param managementCategory the management category
	 */
	void update(Classification managementCategory);
	
	
	
	/**
	 * Gets the all management category.
	 *
	 * @param companyId the company id
	 * @return the all management category
	 */
	List<Classification> getAllManagementCategory(String companyId);

}
