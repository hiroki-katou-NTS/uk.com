/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package employee;

import java.util.List;

import nts.uk.query.app.employee.EmployeeSearchQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeDto;

/**
 * The Interface RegulationInfoEmployeePub.
 */
public interface RegulationInfoEmployeePub {

	/**
	 * Find.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> find(EmployeeSearchQueryDto query);
}
