/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import java.util.Optional;

/**
 * The Interface WeekHolidaySettingRepository.
 */
public interface WeekHolidaySettingRepository {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<WeekHolidaySetting> findByCID(String companyId);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(WeekHolidaySetting domain);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(WeekHolidaySetting domain);
}
