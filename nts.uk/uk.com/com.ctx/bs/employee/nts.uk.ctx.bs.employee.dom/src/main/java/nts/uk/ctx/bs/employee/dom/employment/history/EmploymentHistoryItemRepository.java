package nts.uk.ctx.bs.employee.dom.employment.history;

public interface EmploymentHistoryItemRepository {
	/**
	 * Add employment
	 * @param domain
	 */
	void adḍ̣̣̣(EmploymentHistoryItem domain);
	
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
