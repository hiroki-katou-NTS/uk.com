package nts.uk.ctx.at.function.ac.annualworkschedule;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.ClassificationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.DepartmentImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmploymentImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.PositionImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.WorkplaceImport;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

public class EmployeeInformationAdapterImpl implements EmployeeInformationAdapter{
	
	@Inject
	EmployeeInformationPub employeeInformationPub;

	@Override
	public List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param) {
		// TODO Auto-generated method stub
		EmployeeInformationQueryDto employeeInformationQueryDto = 
				EmployeeInformationQueryDto.builder()
					.employeeIds(param.getEmployeeIds()) 
					.referenceDate(param.getReferenceDate())
					.toGetWorkplace(param.isToGetWorkplace())
					.toGetDepartment(param.isToGetDepartment())
					.toGetPosition(param.isToGetPosition())
					.toGetEmploymentCls(param.isToGetEmployment())
					.toGetClassification(param.isToGetClassification())
					.toGetEmploymentCls(param.isToGetEmploymentCls())
					.build();
		List<EmployeeInformationExport> employeeInformationExport = employeeInformationPub.find(employeeInformationQueryDto);
		if(CollectionUtil.isEmpty(employeeInformationExport)) {
			return Collections.emptyList();
		}
		
		return employeeInformationExport.stream()
				.map(f -> new EmployeeInformationImport(
				f.getEmployeeId(),
				f.getEmployeeCode(),
				f.getBusinessName(),
				new WorkplaceImport(f.getWorkplace().getWorkplaceCode(), f.getWorkplace().getWorkplaceGenericName(), f.getWorkplace().getWorkplaceName()),
				new ClassificationImport(f.getClassification().getClassificationCode(), f.getClassification().getClassificationName()),
				new DepartmentImport(f.getDepartment().getDepartmentCode(), f.getDepartment().getDepartmentName(), f.getDepartment().getDepartmentGenericName()),
				new PositionImport(f.getPosition().getPositionCode(), f.getPosition().getPositionName()),
				new EmploymentImport(f.getEmployment().getEmploymentCode(), f.getEmployment().getEmploymentName()),
				f.getEmploymentCls()
				))
				.collect(Collectors.toList());
	}

	
}
