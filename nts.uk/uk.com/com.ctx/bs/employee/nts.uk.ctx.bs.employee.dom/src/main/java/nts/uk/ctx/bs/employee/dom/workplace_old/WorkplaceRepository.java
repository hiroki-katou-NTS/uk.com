/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace_old;

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
	 * Find by wkp id.
	 *
	 * @param workplaceId the workplace id
	 * @return the optional
	 */
	Optional<Workplace> findByWkpId(String workplaceId);

	/**
	 * Find all history.
	 *
	 * @param companyId the company id
	 * @param generalDate the general date
	 * @return the list
	 */
	List<WorkPlaceHistory> findAllHistory(String companyId, GeneralDate generalDate);

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
	List<Workplace> findByWkpIds(List<String> workplaceIds);

	/**
	 * Find all workplace of company.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the list
	 */
	List<Workplace> findAllWorkplaceOfCompany(String companyId, GeneralDate baseDate);

	/**
	 * Find by wkp cd.
	 *
	 * @param wpkCode the wpk code
	 * @param date the date
	 * @return the list
	 */
	List<Workplace> findByWkpCd(String companyId, String wpkCode, GeneralDate baseDate);
	
	/**
	 * Find by wkp id.
	 *
	 * @param companyId the company id
	 * @param wpkId the wpk id
	 * @param baseDate the base date
	 * @return the list
	 */
	List<Workplace> findByWkpId(String companyId, String wpkId, GeneralDate baseDate);
}
