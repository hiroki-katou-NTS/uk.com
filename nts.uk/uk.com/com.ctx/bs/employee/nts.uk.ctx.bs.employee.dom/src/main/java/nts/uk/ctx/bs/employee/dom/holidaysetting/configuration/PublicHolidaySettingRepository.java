/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import java.util.Optional;

/**
 * The Interface PublicHolidaySettingRepository.
 */
public interface PublicHolidaySettingRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<PublicHolidaySetting> findByCID(String companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(PublicHolidaySetting domain);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(PublicHolidaySetting domain);
}
