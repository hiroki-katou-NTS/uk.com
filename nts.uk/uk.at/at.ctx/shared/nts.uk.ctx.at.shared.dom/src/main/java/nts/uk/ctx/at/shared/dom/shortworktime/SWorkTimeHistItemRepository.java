/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface SWorkTimeHistItemRepository.
 */
public interface SWorkTimeHistItemRepository {

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(ShortWorkTimeHistoryItem domain);
	
	/**
	 * Adds all
	 *
	 * @param domain the domain
	 */
	void addAll(List<ShortWorkTimeHistoryItem> domains);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(ShortWorkTimeHistoryItem domain);
	
	/**
	 * Update all
	 * @author lanlt
	 * @param domain the domain
	 */
	void updateAll(List<ShortWorkTimeHistoryItem> domains);
	
	/**
	 * Find by key.
	 *
	 * @param empId the emp id
	 * @param histId the hist id
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistoryItem> findByKey(String empId, String histId);
	
	void delete(String sid, String hist);
	
	/**
	 * Find by emp and period.
	 *
	 * @param empIdList the emp id list
	 * @param date the date
	 * @return the map
	 */
	List<ShortWorkTimeHistoryItem> findByHistIds(List<String> histIds);

	List<Object[]> findByHistIdsCPS013(List<String> histIds);
	
	/**
	 * 社員の短時間勤務履歴を期間で取得する
	 * 
	 * @param employeeIds
	 * @param period
	 * @return
	 */
	List<ShortWorkTimeHistoryItem> findWithSidDatePeriod(String companyId, List<String> employeeIds, DatePeriod period);
}
