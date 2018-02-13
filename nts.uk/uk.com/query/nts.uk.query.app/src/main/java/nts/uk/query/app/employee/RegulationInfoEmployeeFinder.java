/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	 * @param queryDto the query dto
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> find(EmployeeSearchQueryDto queryDto) {
		return this.repo.find(queryDto.toQueryModel()).stream()
				.map(model -> RegulationInfoEmployeeDto.builder().employeeCode(model.getEmployeeCode())
						.employeeId(model.getEmployeeID()).employeeName(model.getName().get()).build())
				.collect(Collectors.toList());
	}
}
