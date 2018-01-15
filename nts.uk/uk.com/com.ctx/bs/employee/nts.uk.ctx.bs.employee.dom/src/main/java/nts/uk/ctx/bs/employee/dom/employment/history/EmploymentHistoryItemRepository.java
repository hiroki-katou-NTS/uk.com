package nts.uk.ctx.bs.employee.dom.employment.history;

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
	
	
}
