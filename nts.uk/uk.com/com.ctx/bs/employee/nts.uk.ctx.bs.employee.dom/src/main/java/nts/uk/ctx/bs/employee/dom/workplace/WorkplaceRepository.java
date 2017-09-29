/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;
import java.util.Optional;

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
	void add(Workplace wkp);

	/**
	 * Update.
	 *
	 * @param wkp the wkp
	 */
	void update(Workplace wkp);

	/**
	 * Removes the by wkp id.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 */
    void removeByWkpId(String companyId, String workplaceId);

	/**
	 * Find by start date.
	 *
	 * @param companyId the company id
	 * @param date the date
	 * @return the list
	 */
    List<Workplace> findByStartDate(String companyId, GeneralDate date);
	
	
	/**
	 * Find all workplace.
	 *
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<Workplace> findByWkpIds(List<String> workplaceIds);
	
	/**
	 * Find latest by workplace id.
	 *
	 * @param workplaceId the workplace id
	 * @return the optional
	 */
	Optional<Workplace> findLatestByWorkplaceId(String workplaceId);

	/**
	 * Find by workplace id.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the optional
	 */
    Optional<Workplace> findByWorkplaceId(String companyId, String workplaceId);
}
