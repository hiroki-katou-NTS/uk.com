/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import java.util.Optional;

/**
 * The Interface CommonGuidelineSettingRepository.
 */
public interface CommonGuidelineSettingRepository {

	/**
 	 * Adds the.
 	 *
 	 * @param patternCalendar the pattern calendar
 	 */
    void add(CommonGuidelineSetting patternCalendar);

	/**
     * Update.
     *
     * @param patternCalendar the pattern calendar
     */
    void update(CommonGuidelineSetting patternCalendar);

	/**
	 * Find by code.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
    Optional<CommonGuidelineSetting> findByCompanyId(String companyId);
}
