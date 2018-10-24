/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.info;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceInfoRepository.
 */
public interface WorkplaceInfoRepository {

	/**
	 * Adds the.
	 *
	 * @param workplaceInfo the workplace info
	 */
    void add(WorkplaceInfo workplaceInfo);

	/**
	 * Update.
	 *
	 * @param workplaceInfo the workplace info
	 */
	void update(WorkplaceInfo workplaceInfo);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param historyId the history id
	 */
    void remove(String companyId, String workplaceId, String historyId);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param wkpId the wkp id
	 * @param historyId the history id
	 * @return the optional
	 */
	Optional<WorkplaceInfo> find(String companyId, String wkpId, String historyId);

	/**
	 * Find by wkp cd.
	 *
	 * @param companyId the company id
	 * @param wpkCode the wpk code
	 * @param baseDate the base date
	 * @return the list
	 */
	List<WorkplaceInfo> findByWkpCd(String companyId, String wpkCode, GeneralDate baseDate);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @return the list
	 */
	List<WorkplaceInfo> findAll(String companyId, GeneralDate baseDate);

	/**
	 * Find by wkp id.
	 *
	 * @param wkpId the wkp id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<WorkplaceInfo> findByWkpId(String wkpId, GeneralDate baseDate);
	
	/**
	 * Find by base date wkp ids.
	 *
	 * @param companyId the company id
	 * @param baseDate the base date
	 * @param wkpIds the wkp ids
	 * @return the list
	 */
	List<WorkplaceInfo> findByBaseDateWkpIds(String companyId, GeneralDate baseDate, List<String> wkpIds);

	/**
	 * Checks if is existed wkp cd.
	 *
	 * @param companyId the company id
	 * @param newWkpCd the new wkp cd
	 * @return true, if is existed wkp cd
	 */
	boolean isExistedWkpCd(String companyId, String newWkpCd);
	
	/**
	 * Gets the by wkp ids.
	 *
	 * @param wkpIds the wkp ids
	 * @param baseDate the base date
	 * @return the by wkp ids
	 */
	List<WorkplaceInfo> getByWkpIds(List<String> wkpIds, GeneralDate baseDate);
	
	/**
	 * Find by wkp ids.
	 *
	 * @param companyId the company id
	 * @param wkpIds the wkp ids
	 * @return the list
	 */
	List<WorkplaceInfo> findByWkpIds(String companyId, List<String> wkpIds);
	
	/**
	 * Find by history id.
	 *
	 * @param historyId the history id
	 * @return the list
	 */
	List<WorkplaceInfo> findByHistory(List<String> historyList, String companyId);
	
	/**
	 * Find by history.
	 *
	 * @param historyList the history list
	 * @return the list
	 */
	List<WorkplaceInfo> findByHistory(List<String> historyList);
	
	/**
	 * Find by wkp ids and hist ids.
	 *
	 * @param companyId the company id
	 * @param wkpIds the wkp ids
	 * @param histIds the hist ids
	 * @return the list
	 */
	List<WorkplaceInfo> findByWkpIdsAndHistIds(String companyId, List<String> wkpIds, List<String> histIds);
	
	/**
	 * Find by wkp cds.
	 *
	 * @param companyId the company id
	 * @param wpkCodes the wpk codes
	 * @param baseDates the base dates
	 * @return the map
	 */
	Map<GeneralDate, List<WorkplaceInfo>> findByWkpIds(String companyId, List<String> wpkIds, List<GeneralDate> baseDates);
}
