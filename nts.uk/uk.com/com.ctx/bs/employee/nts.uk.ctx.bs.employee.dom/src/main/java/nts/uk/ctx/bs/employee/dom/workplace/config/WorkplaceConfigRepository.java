/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config;

import java.util.Optional;

import nts.arc.time.GeneralDate;

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
	 * Update.
	 *
	 * @param wkpConfig the wkp config
	 * @param endĐate the end đate
	 */
	void update(WorkplaceConfig wkpConfig,GeneralDate endĐate);
	
	/**
	 * Find all by company id.
	 *
	 * @param companyId the company id
	 * @return the workplace config
	 */
	WorkplaceConfig findAllByCompanyId(String companyId);
	
	/**
	 * Find by hist id.
	 *
	 * @param companyId the company id
	 * @param prevHistId the prev hist id
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findByHistId(String companyId,String prevHistId);
	
	/**
	 * Find lastest by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findLastestByCompanyId(String companyId);
}
