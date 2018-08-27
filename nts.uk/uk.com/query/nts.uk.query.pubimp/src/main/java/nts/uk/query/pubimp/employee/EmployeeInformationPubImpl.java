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
import nts.uk.query.pub.classification.ClassificationExport;
import nts.uk.query.pub.department.DepartmentExport;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.query.pub.employement.EmploymentExport;
import nts.uk.query.pub.position.PositionExport;
import nts.uk.query.pub.workplace.WorkplaceExport;

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
			ClassificationExport cls = item.getClassification().isPresent() ? ClassificationExport.builder()
					.classificationCode(item.getClassification().get().getClassificationCode())
					.classificationName(item.getClassification().get().getClassificationName())
					.build() : null;

			WorkplaceExport wkp = item.getWorkplace().isPresent() ? WorkplaceExport.builder()
					.workplaceId(item.getWorkplace().get().getWorkplaceId())
					.workplaceCode(item.getWorkplace().get().getWorkplaceCode())
					.workplaceName(item.getWorkplace().get().getWorkplaceName())
					.workplaceGenericName(item.getWorkplace().get().getWorkplaceGenericName())
					.build() : null;

			DepartmentExport dep = item.getDepartment().isPresent() ? DepartmentExport.builder()
					.departmentCode(item.getDepartment().get().getDepartmentCode())
					.departmentGenericName(item.getDepartment().get().getDepartmentGenericName())
					.departmentName(item.getDepartment().get().getDepartmentName())
					.build() : null;

			PositionExport pos = item.getPosition().isPresent() ? PositionExport.builder()
					.positionId(item.getPosition().get().getPositionId())
					.positionCode(item.getPosition().get().getPositionCode())
					.positionName(item.getPosition().get().getPositionName())
					.build(): null;

			EmploymentExport emp = item.getEmployment().isPresent() ? EmploymentExport.builder()
					.employmentCode(item.getEmployment().get().getEmploymentCode())
					.employmentName(item.getEmployment().get().getEmploymentName())
					.build() : null;

			return EmployeeInformationExport.builder()
				.businessName(item.getBusinessName())
				.employeeCode(item.getEmployeeCode())
				.employeeId(item.getEmployeeId())
				.classification(cls)
				.workplace(wkp)
				.department(dep)
				.position(pos)
				.employment(emp)
				.employmentCls(item.getEmploymentCls().orElse(null))
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
