package nts.uk.ctx.bs.employee.dom.employment.history;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.Employment;

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
	
	Optional<Employment> getDetailEmploymentHistoryItem(String sid, GeneralDate date);
}
