/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import java.util.List;

import nts.arc.time.GeneralDateTime;

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
	 * Find work place id by role.
	 *
	 * @param baseDate the base date
	 * @param referenceRange the reference range
	 * @return the list
	 */
	public List<String> findWorkPlaceIdByRole(GeneralDateTime baseDate, Integer referenceRange);

}
