/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;

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
	
	void update(String sid, DateHistoryItem histItem);
	
	void delete(String sid, String histId);
}
