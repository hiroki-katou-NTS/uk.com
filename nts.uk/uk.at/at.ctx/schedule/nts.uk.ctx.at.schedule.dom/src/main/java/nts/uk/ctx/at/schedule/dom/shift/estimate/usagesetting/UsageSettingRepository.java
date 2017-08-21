/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting;

import java.util.Optional;

/**
 * The Interface UsageSettingRepository.
 */
public interface UsageSettingRepository {

	/**
	 * Adds the.
	 *
	 * @param patternCalendar the pattern calendar
	 */
    void add(UsageSetting patternCalendar);

	/**
	 * Update.
	 *
	 * @param patternCalendar the pattern calendar
	 */
    void update(UsageSetting patternCalendar);

	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
    Optional<UsageSetting> findByCompanyId(String companyId);
}
