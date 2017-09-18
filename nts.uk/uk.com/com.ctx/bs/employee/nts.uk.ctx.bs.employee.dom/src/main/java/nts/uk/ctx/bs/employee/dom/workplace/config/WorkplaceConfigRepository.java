/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config;

import java.util.Optional;

/**
 * The Interface WorkplaceConfigRepository.
 */
public interface WorkplaceConfigRepository {

	/**
	 * Adds the.
	 *
	 * @param workplaceConfig the workplace config
	 * @return the string
	 */
	String add(WorkplaceConfig workplaceConfig);
	
	/**
	 * Find all by company id.
	 *
	 * @param companyId the company id
	 * @return the workplace config
	 */
	WorkplaceConfig findAllByCompanyId(String companyId);
	
	/**
	 * Find lastest by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findLastestByCompanyId(String companyId);
}
