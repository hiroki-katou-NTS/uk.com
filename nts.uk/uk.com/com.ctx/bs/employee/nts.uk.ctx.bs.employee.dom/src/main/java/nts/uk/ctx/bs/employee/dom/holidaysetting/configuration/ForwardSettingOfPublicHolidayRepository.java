/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import java.util.Optional;

/**
 * The Interface ForwardSettingOfPublicHolidayRepository.
 */
public interface ForwardSettingOfPublicHolidayRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<ForwardSettingOfPublicHoliday> findByCID(String companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(ForwardSettingOfPublicHoliday domain);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(ForwardSettingOfPublicHoliday domain);
}
