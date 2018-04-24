/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;

/**
 * The Class JpaEmployeeInformationRepository.
 */
@Stateless
public class JpaEmployeeInformationRepository implements EmployeeInformationRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.employee.EmployeeInformationRepository#find(nts.uk.
	 * query.model.employee.EmployeeInformationQuery)
	 */
	@Override
	public List<EmployeeInformation> find(EmployeeInformationQuery param) {
		// TODO Auto-generated method stub
		return null;
	}

}
