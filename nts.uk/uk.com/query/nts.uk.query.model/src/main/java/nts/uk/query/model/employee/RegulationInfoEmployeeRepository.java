/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeeQueryModelRepository.
 */
public interface RegulationInfoEmployeeRepository {
	
	/**
	 * Find.
	 *
	 * @param comId the com id
	 * @param paramQuery the param query
	 * @return the list
	 */
	public List<RegulationInfoEmployee> find(String comId, EmployeeSearchQuery paramQuery);

	/**
	 * Find info by S ids.
	 *
	 * @param sIds the s ids
	 * @param referenceDate the reference date
	 * @return the list
	 */
	public List<RegulationInfoEmployee> findInfoBySIds(List<String> sIds, GeneralDate referenceDate);

}
