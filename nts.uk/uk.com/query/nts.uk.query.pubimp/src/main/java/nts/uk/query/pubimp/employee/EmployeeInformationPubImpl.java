/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

/**
 * The Class EmployeeInformationPubImpl.
 */
@Stateless
public class EmployeeInformationPubImpl implements EmployeeInformationPub {

	/** The repo. */
	@Inject
	private EmployeeInformationRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.EmployeeInformationPub#find(nts.uk.query.pub.
	 * employee.EmployeeInformationQueryDto)
	 */
	@Override
	public List<EmployeeInformationExport> find(EmployeeInformationQueryDto param) {
		return this.repo.find(this.toQueryModel(param)).stream().map(item -> {
			return EmployeeInformationExport.builder()
					.employeeId(item.getEmployeeId())
					.employeeCode(item.getEmployeeCode())
					.businessName(item.getBusinessName())
					.classification(item.getClassification())
					.department(item.getDepartment())
					.employment(item.getEmployment())
					.employmentCls(item.getEmploymentCls())
					.position(item.getPosition())
					.workplace(item.getWorkplace())
					.build();
		}).collect(Collectors.toList());
	}

	/**
	 * To query model.
	 *
	 * @param dto the dto
	 * @return the employee information query
	 */
	private EmployeeInformationQuery toQueryModel(EmployeeInformationQueryDto dto) {
		return EmployeeInformationQuery.builder()
				.employeeIds(dto.getEmployeeIds())
				.referenceDate(dto.getReferenceDate())
				.toGetClassification(dto.isToGetClassification())
				.toGetDepartment(dto.isToGetDepartment())
				.toGetEmployment(dto.isToGetEmployment())
				.toGetEmploymentCls(dto.isToGetEmploymentCls())
				.toGetPosition(dto.isToGetPosition())
				.toGetWorkplace(dto.isToGetWorkplace())
				.build();
	}

}
