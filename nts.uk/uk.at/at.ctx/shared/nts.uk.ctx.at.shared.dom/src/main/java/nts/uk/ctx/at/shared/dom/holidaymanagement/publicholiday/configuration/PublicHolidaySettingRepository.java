/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import java.util.List;
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
	Optional<PublicHolidaySetting> get(String companyId);
	
	/**
	 * Find by CID to list.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<PublicHolidaySetting> findByCIDToList(String companyId);
	
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
	void insert(PublicHolidaySetting domain);
}
