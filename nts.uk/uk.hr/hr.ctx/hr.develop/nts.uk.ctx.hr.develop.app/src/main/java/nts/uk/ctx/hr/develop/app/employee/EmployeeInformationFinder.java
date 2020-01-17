package nts.uk.ctx.hr.develop.app.employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;

@Stateless
public class EmployeeInformationFinder {

	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;

	public EmployeeInformationImport findEmployeeInfo(EmployeeInformationQuery query) {

		List<EmployeeInformationImport> empInfos = this.empInfoAdaptor.getEmployeeInfos(
				Optional.ofNullable(Arrays.asList(query.getPersonId())), Arrays.asList(query.getEmployeeId()),
				query.getBaseDate(), Optional.ofNullable(query.getGetDepartment()),
				Optional.ofNullable(query.getGetPosition()), Optional.ofNullable(query.getGetEmployment()));

		if (!empInfos.isEmpty()) {
			return empInfos.get(0);
		}

		return null;
	}
}
