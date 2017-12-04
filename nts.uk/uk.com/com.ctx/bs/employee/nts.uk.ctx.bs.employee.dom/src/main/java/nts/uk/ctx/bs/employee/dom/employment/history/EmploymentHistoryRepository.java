package nts.uk.ctx.bs.employee.dom.employment.history;

import java.util.Optional;

import nts.uk.shr.com.history.DateHistoryItem;

public interface EmploymentHistoryRepository {
	/**
	 * Get employment history by employee id
	 * @param sid
	 * @return
	 */
	Optional<EmploymentHistory> getByEmployeeId(String sid);
	
	/**
	 * Add employment history
	 * @param sid
	 * @param domain
	 */
	void add(String sid, DateHistoryItem domain);
	
	/**
	 * Update employment history
	 * @param itemToBeUpdated
	 */
	void update(DateHistoryItem itemToBeUpdated);
	
	/**
	 * Delete employment history
	 * @param histId
	 */
	void delete(String histId);
}
