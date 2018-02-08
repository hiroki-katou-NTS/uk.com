/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import java.util.Optional;

/**
 * The Interface PublicHolidayManagementUsageUnitRepository.
 */
public interface PublicHolidayManagementUsageUnitRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<PublicHolidayManagementUsageUnit> findByCID(String companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(PublicHolidayManagementUsageUnit domain);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(PublicHolidayManagementUsageUnit domain);
}
