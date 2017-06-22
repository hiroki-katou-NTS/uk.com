/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace.history;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface WorkplaceHistoryRepository.
 */
public interface WorkplaceHistoryRepository {
	
	/**
	 * Search employee.
	 *
	 * @param baseDate the base date
	 * @param workplaces the workplaces
	 * @return the list
	 */
	public List<WorkplaceHistory> searchEmployee(GeneralDate baseDate, List<String> workplaces);
}
