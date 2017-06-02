/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import java.util.List;

/**
 * The Interface EmploymentRepository.
 */
public interface EmploymentRepository {
	
	/**
	 * Find all.
	 *
	 * @param CompanyId the company id
	 * @return the list
	 */
	List<Employment> findAll(String CompanyId);
}
