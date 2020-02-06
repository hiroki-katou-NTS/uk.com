/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface SWorkTimeHistoryRepository.
 */
public interface SWorkTimeHistoryRepository {

	/**
	 * Find by key.
	 *
	 * @param empId the emp id
	 * @param histId the hist id
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistory> findByKey(String empId, String histId);

	/**
	 * Find by base date.
	 *
	 * @param empId the emp id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistory> findByBaseDate(String empId, GeneralDate baseDate);
	
	
	Optional <ShortWorkTimeHistory> getBySid(String cid, String sid);
	
	Optional <ShortWorkTimeHistory> getBySidDesc(String cid, String sid);
	
	void add(String cid, String sid, DateHistoryItem histItem);
	
	/**
	 * @author lanlt
	 * addAll BshmtWorktimeHist
	 * @param histItem
	 */
	void addAll(Map<String, DateHistoryItem> histItem);
	
	void update(String sid, DateHistoryItem histItem);
	
	void updateAll(Map<String, DateHistoryItem> histItem);
	
	void delete(String sid, String histId);
	
	/**
	 * Find by emp and period.
	 *
	 * @param empIdList the emp id list
	 * @param date the date
	 * @return the map
	 */
	Map<String, ShortWorkTimeHistory> findByEmpAndPeriod(List<String> empIdList, DatePeriod period);
	
	/**
	 * Find lst by emp and period.
	 *
	 * @param empIdList the emp id list
	 * @param date the date
	 * @return the list
	 */
	List<ShortWorkTimeHistory> findLstByEmpAndPeriod(List<String> empIdList, DatePeriod period);
	
	
	/**
	 * get ShortWorkTimeHis 
	 * @param cid
	 * @param sids
	 * @param baseDate
	 * @author lanlt
	 */
	List<DateHistoryItem> finsLstBySidsAndCidAndDate(String cid, List<String> sids, GeneralDate baseDate);

	/**
	 * @author lanlt
	 * get All ShortWorkTimeHis 
	 * @param cid
	 * @param sids
	 * @return
	 */
	List <ShortWorkTimeHistory> getBySidsAndCid(String cid, List<String> sids);

	// for cps013
	List<DateHistoryItem> getByEmployeeListNoWithPeriod(String cid, List<String> sids);
}
