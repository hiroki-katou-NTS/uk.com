package nts.uk.ctx.hr.develop.app.employee;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;

@Stateless
public class EmployeeInformationFinder {

	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;

	public EmployeeInformationImport findEmployeeInfo(EmployeeInformationQuery query) {

		EmployeeInfoQueryImport param = EmployeeInfoQueryImport.builder().employeeIds(Arrays.asList(query.getEmployeeId()))
				.referenceDate(query.getBaseDate()).toGetDepartment(query.getDispDepartment())
				.toGetEmployment(query.getDispEmployment()).toGetPosition(query.getDispPosition()).toGetWorkplace(false)
				.toGetClassification(false).toGetEmploymentCls(false).build();

		List<EmployeeInformationImport> empInfos = this.empInfoAdaptor.find(param);

		if (!empInfos.isEmpty()) {
			return empInfos.get(0);
		}

		return null;
	}
}
