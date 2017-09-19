/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceRepository.
 */
public interface WorkplaceRepository {

	/**
	 * Adds the.
	 *
	 * @param wkp the wkp
	 * @return the string
	 */
	String add(Workplace wkp);
	
	/**
	 * Update latest history.
	 *
	 * @param wkp the wkp
	 */
	void updateLatestHistory(Workplace wkp);
	
	/**
	 * Removes the latest history.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 */
	void removeLatestHistory(String companyId,String workplaceId);
	
	/**
	 * Find by start date.
	 *
	 * @param companyId the company id
	 * @param date the date
	 * @return the list
	 */
	List<Workplace> findByStartDate(String companyId,GeneralDate date);
}
