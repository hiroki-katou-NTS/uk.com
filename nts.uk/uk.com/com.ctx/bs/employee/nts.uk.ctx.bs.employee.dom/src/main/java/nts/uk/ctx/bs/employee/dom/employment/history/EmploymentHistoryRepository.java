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
	 * @param domain
	 */
	void add(EmploymentHistory domain);
	
	/**
	 * Update employment history
	 * @param domain
	 * @param itemToBeUpdated
	 */
	void update(EmploymentHistory domain, DateHistoryItem itemToBeUpdated);
	
	/**
	 * Delete employment history
	 * @param domain
	 * @param itemToBeDeleted
	 */
	void delete(EmploymentHistory domain, DateHistoryItem itemToBeDeleted);
}
