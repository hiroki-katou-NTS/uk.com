/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface AffiliationWorkplaceHistoryRepository.
 */
public interface AffiliationWorkplaceHistoryRepository {
	
	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param workplaces the workplaces
	 * @return the list
	 */
	public List<AffiliationWorkplaceHistory> searchWorkplaceHistory(GeneralDate baseDate, List<String> workplaces);
	
	/**
	 * Search employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @param workplaces the workplaces
	 * @return the list
	 */
	public List<AffiliationWorkplaceHistory> searchWorkplaceHistory(List<String> employeeIds, 
			GeneralDate baseDate, List<String> workplaces);
	
	/**
	 * Search workplace history by employee.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<AffiliationWorkplaceHistory> searchWorkplaceHistoryByEmployee(String employeeId, 
			GeneralDate baseDate);
	
	
	/**
	 * Search workplace of company id.
	 *
	 * @param employeeIds the employee ids
	 * @param baseDate the base date
	 * @return the list
	 */
	public List<AffiliationWorkplaceHistory> searchWorkplaceOfCompanyId(List<String> employeeIds,
			GeneralDate baseDate);
}
