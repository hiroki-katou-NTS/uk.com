/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import employee.RegulationInfoEmployeePub;
import nts.uk.query.app.employee.EmployeeSearchQueryDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeDto;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;

/**
 * The Class RegulationInfoEmployeePubImpl.
 */
@Stateless
public class RegulationInfoEmployeePubImpl implements RegulationInfoEmployeePub {

	/** The employee finder. */
	@Inject
	private RegulationInfoEmployeeFinder employeeFinder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see employee.RegulationInfoEmployeePub#find(nts.uk.query.app.employee.
	 * EmployeeSearchQueryDto)
	 */
	@Override
	public List<RegulationInfoEmployeeDto> find(EmployeeSearchQueryDto query) {
		return this.employeeFinder.find(query);
	}
}
