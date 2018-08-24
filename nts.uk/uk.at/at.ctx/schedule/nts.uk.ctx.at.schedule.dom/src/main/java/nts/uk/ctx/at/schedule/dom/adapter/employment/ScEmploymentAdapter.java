/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.employment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Class ScClassificationAdapter.
 */
public interface ScEmploymentAdapter {

	/**
	 * Find by date.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate);
	
	Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes);

}
