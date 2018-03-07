/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.List;

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
	public List<RegulationInfoEmployeeExport> find(EmployeeSearchQueryDto query);
}
