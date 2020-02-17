package nts.uk.ctx.at.shared.dom.adapter.workplace.config.info;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplaceConfigInfoAdapter {
	/**
	 * Find by history ids and wpl ids.
	 *
	 * @param companyId
	 *            the company id
	 * @param historyIds
	 *            the history ids
	 * @param workplaceIds
	 *            the workplace ids -> if null or emplty, return all workplace
	 *            config info.
	 * @return the list
	 */
	List<WorkPlaceConfigInfoImport> findByHistoryIdsAndWplIds(String companyId, List<String> historyIds,
			List<String> workplaceIds);
	
	List<JobTitleExport> findAllById(String companyId,List<String> positionIds ,GeneralDate baseDate );
	
	/**
	 * [No.560]職場IDから職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listWorkplaceId
	 * @param baseDate
	 * @return
	 */
	List<WorkplaceInfor> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId, GeneralDate baseDate);
}
