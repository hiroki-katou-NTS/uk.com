/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegulationInfoEmployeePubImpl.
 */
@Stateless
public class RegulationInfoEmployeePubImpl implements RegulationInfoEmployeePub {

	/** The emp repo. */
	@Inject
	private RegulationInfoEmployeeRepository empRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see employee.RegulationInfoEmployeePub#find(nts.uk.query.app.employee.
	 * EmployeeSearchQueryDto)
	 */
	@Override
	public List<RegulationInfoEmployeeExport> find(EmployeeSearchQueryDto query) {
		return this.empRepo.find(AppContexts.user().companyId(), query.toQueryModel()).stream()
				.map(model -> RegulationInfoEmployeeExport.builder()
						.employeeCode(model.getEmployeeCode())
						.employeeId(model.getEmployeeID())
						.employeeName(model.getName().orElse(""))
						.workplaceId(model.getWorkplaceId().orElse(""))
						.workplaceCode(model.getWorkplaceCode().orElse(""))
						.workplaceName(model.getWorkplaceName().orElse(""))
						.build())
				.collect(Collectors.toList());
	}
}
