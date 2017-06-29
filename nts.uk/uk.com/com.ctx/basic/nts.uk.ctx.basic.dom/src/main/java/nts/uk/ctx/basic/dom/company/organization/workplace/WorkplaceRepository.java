/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceRepository.
 */
public interface WorkplaceRepository {
	
	/**
	 * Adds the.
	 *
	 * @param workplace the workplace
	 */
	void add(Workplace workplace);
	
	/**
	 * Update.
	 *
	 * @param workplace the workplace
	 */
	void update(Workplace workplace);
	
	/**
	 * Find all history.
	 *
	 * @param companyId the company id
	 * @param generalDate the general date
	 * @return the list
	 */
	List<WorkPlaceHistory> findAllHistory(String companyId,GeneralDate generalDate);
	
	/**
	 * Find all hierarchy.
	 *
	 * @param historyId the history id
	 * @return the list
	 */
	List<WorkPlaceHierarchy> findAllHierarchy(String historyId);
	
	/**
	 * Find all hierarchy child.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<WorkPlaceHierarchy> findAllHierarchyChild(String companyId, String workplaceId);
	
	/**
	 * Find all workplace.
	 *
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	List<Workplace> findAllWorkplace(String workplaceId);
	
	
	/**
	 * Gets the all workplace of company.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the all workplace of company
	 */
	List<Workplace> getAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);
}
