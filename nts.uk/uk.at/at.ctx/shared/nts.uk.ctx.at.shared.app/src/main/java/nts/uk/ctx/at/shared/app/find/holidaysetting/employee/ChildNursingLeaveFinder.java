package nts.uk.ctx.at.shared.app.find.holidaysetting.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.EmployeeBasicInfoDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.arc.time.GeneralDate;

@Stateless
public class ChildNursingLeaveFinder {
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	public ManagementClassificationByEmployeeDto startPage(List<String> sIDs, GeneralDate baseDate) {
		List<EmployeeImport> lstEmp = empEmployeeAdapter.findByEmpId(sIDs);
		List<EmployeeBasicInfoDto> lstEmpRs =  lstEmp.stream().map(item -> {
			return  EmployeeBasicInfoDto
						.builder()
						.employeeCode(item.getEmployeeCode())
						.employeeId(item.getEmployeeId())
						.employeeName(item.getEmployeeName())
						.build()
		})
	}
}
