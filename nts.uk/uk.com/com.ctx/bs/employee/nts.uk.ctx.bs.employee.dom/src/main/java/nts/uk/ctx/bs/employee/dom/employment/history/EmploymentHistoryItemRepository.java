package nts.uk.ctx.bs.employee.dom.employment.history;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;

public interface EmploymentHistoryItemRepository {
	
	
	Optional<EmploymentInfo> getDetailEmploymentHistoryItem(String companyId, String sid, GeneralDate date);
	
	/**
	 * get with historyId
	 * @param historyId
	 * @return
	 */
	Optional<EmploymentHistoryItem> getByHistoryId(String historyId);
	
	/**
	 * Add employment
	 * @param domain
	 */
	void add(EmploymentHistoryItem domain);
	
	/**
	 * Update employment
	 * @param domain
	 */
	void update(EmploymentHistoryItem domain);
	
	/**
	 * Delete employment
	 * @param domain
	 */
	void delete(String histId);
	
	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<EmploymentHistoryItem> searchEmployee(GeneralDate baseDate, List<String> employmentCodes );

	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param employmentCodes the employment codes
	 * @return the list
	 */
	List<EmploymentHistoryItem> searchEmployee(List<String> employeeIds, 
			GeneralDate baseDate, List<String> employmentCodes );

	/**
	 * Search employment of sids.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	List<EmploymentHistoryItem> searchEmploymentOfSids(List<String> employeeIds,
			GeneralDate baseDate);

}
