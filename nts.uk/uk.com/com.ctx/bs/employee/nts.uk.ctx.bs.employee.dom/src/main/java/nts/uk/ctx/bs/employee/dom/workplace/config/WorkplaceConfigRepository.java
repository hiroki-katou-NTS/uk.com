/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface WorkplaceConfigRepository.
 */
public interface WorkplaceConfigRepository {

	/**
	 * Adds the.
	 *
	 * @param workplaceConfig the workplace config
	 */
	void add(WorkplaceConfig workplaceConfig);

	/**
	 * Update.
	 *
	 * @param wkpConfig the wkp config
	 */
	void update(WorkplaceConfig wkpConfig);

	/**
     * Removes the wkp config hist.
     *
     * @param companyId the company id
     * @param historyId the history id
     */
    void removeWkpConfigHist(String companyId, String historyId);

	/**
     * Find workplace by company id.
     *
     * @param companyId the company id
     * @return the optional
     */
    Optional<WorkplaceConfig> findAllByCompanyId(String companyId);

	/**
	 * Find by hist id.
	 *
	 * @param companyId the company id
	 * @param prevHistId the prev hist id
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findByHistId(String companyId, String prevHistId);

	/**
	 * Find by start date.
	 *
	 * @param companyId the company id
	 * @param startDate the start date
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findByStartDate(String companyId, GeneralDate startDate);

	/**
	 * Find by base date.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkplaceConfig> findByBaseDate(String companyId, GeneralDate baseDate);
	
	/**
	 * Find by company id and period.
	 *
	 * @param companyId the company id
	 * @param period the period
	 * @return the list
	 */
	List<WorkplaceConfig> findByCompanyIdAndPeriod(String companyId, DatePeriod period);

}
