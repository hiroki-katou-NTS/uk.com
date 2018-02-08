/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;

/**
 * The Class RegulationInfoEmployeeFinder.
 */
@Stateless
public class RegulationInfoEmployeeFinder {

	/** The repo. */
	@Inject
	private RegulationInfoEmployeeRepository repo;

	/**
	 * Find.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployee> find(EmployeeSearchQueryDto queryDto) {
		return this.repo.find(queryDto.toQueryModel());
	}
}
