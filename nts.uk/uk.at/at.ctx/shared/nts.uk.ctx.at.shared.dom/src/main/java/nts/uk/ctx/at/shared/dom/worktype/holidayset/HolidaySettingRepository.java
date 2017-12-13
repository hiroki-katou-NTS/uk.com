/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype.holidayset;

import java.util.Optional;

/**
 * The Interface HolidaySettingRepository.
 */
public interface HolidaySettingRepository {

	/**
	 * Find by.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<HolidaySetting> findBy(String companyId);

}
