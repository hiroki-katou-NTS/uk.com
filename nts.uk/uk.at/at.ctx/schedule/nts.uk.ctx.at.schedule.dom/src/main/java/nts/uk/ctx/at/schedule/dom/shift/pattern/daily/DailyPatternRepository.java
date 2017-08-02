/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import java.util.List;
import java.util.Optional;

/**
 * The Interface DailyPatternRepository.
 */
public interface DailyPatternRepository {

	/**
	 * Gets the all patt calendar.
	 *
	 * @param companyId the company id
	 * @return the all patt calendar
	 */
	List<DailyPattern> getAllPattCalendar(String companyId);

	/**
 	 * Adds the.
 	 *
 	 * @param patternCalendar the pattern calendar
 	 */
    void add(DailyPattern patternCalendar);

	/**
     * Update.
     *
     * @param patternCalendar the pattern calendar
     */
    void update(DailyPattern patternCalendar);

	/**
     * Find by company id.
     *
     * @param companyId the company id
     * @param patternCd the pattern cd
     * @return the list
     */
    Optional<DailyPattern> findByCode(String companyId,String patternCd);

	/**
     * Deleted.
     *
     * @param cid the cid
     * @param patternCd the pattern cd
     */
    void delete(String cid, String patternCd);
}
